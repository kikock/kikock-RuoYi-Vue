package com.ruoyi.flowable.framework.code.behavior.script.impl;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.flowable.framework.code.behavior.script.IBpmTaskAssignScript;
import com.ruoyi.flowable.service.task.IBpmProcessInstanceService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Set;

import static com.ruoyi.common.utils.collection.SetUtils.asSet;
import static java.util.Collections.emptySet;

/**
 * 分配给发起人的 Leader 审批的 Script 实现类
 * 目前 Leader 的定义是，
 *
 * @author 芋道源码
 */
public abstract class IBpmTaskAssignLeaderAbstractScript implements IBpmTaskAssignScript{


    @Resource
    @Lazy // 解决循环依赖
    private ISysDeptService deptService;
    @Resource
    @Lazy // 解决循环依赖
    private ISysUserService userService;
    @Resource
    @Lazy // 解决循环依赖
    private IBpmProcessInstanceService bpmProcessInstanceService;

    protected Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, int level){
        Assert.isTrue(level > 0, "level 必须大于 0");
        // 获得发起人
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
        //获取发起人id
        String startUserId = processInstance.getStartUserId();
        // 获得对应 leve 的部门
        SysDept dept = null;
        for (int i = 0; i < level; i++) {
            // 获得 level 对应的部门
            if (dept == null) {
                // 发起人的部门
                SysUser sysUser = userService.selectUserById(Long.valueOf(startUserId));
                if (sysUser == null) { // 找不到发起人的部门，所以无法使用该规则
                    return emptySet();
                }
                dept = sysUser.getDept();
                if (dept == null) {
                    return emptySet();
                }
            } else {
                SysDept parentDept = deptService.selectDeptById(dept.getParentId());
                if (parentDept == null) { // 找不到父级部门，所以只好结束寻找。原因是：例如说，级别比较高的人，所在部门层级比较少
                    break;
                }
                dept = parentDept;
            }
        }
        return asSet(0L);
        //TODO 这里需要优化，需要修改系统部门负责人 保存为用户id ,并进行选择
//        return dept.getLeader() != null ? asSet(dept.getLeader()) : emptySet();
    }


}
