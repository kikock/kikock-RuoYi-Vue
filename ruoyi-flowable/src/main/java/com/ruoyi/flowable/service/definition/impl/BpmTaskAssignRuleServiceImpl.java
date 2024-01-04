package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ObjectUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.framework.code.enums.BpmTaskAssignRuleTypeEnum;
import com.ruoyi.flowable.framework.utils.BpmnModelUtils;
import com.ruoyi.flowable.mapper.definition.BpmTaskAssignRuleMapper;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.definition.IBpmUserGroupService;
import com.ruoyi.system.service.*;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bpm 任务规则Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Service
public class BpmTaskAssignRuleServiceImpl implements IBpmTaskAssignRuleService{
    @Resource
    private BpmTaskAssignRuleMapper bpmTaskAssignRuleMapper;
    @Autowired
    private IBpmModelService bpmModelService;
    @Autowired
    private IBpmProcessDefinitionService bpmProcessDefinitionService;

    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysPostService postService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IBpmUserGroupService userGroupService;
    @Autowired
    private ISysDictDataService dictDataService;


    /**
     * 查询Bpm 任务规则
     *
     * @param id Bpm 任务规则主键
     * @return Bpm 任务规则
     */
    @Override
    public BpmTaskAssignRule selectBpmTaskAssignRuleById(Long id){
        return bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleById(id);
    }

    /**
     * 查询Bpm 任务规则列表
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return Bpm 任务规则
     */
    @Override
    public List<BpmTaskAssignRule> selectBpmTaskAssignRuleList(BpmTaskAssignRule bpmTaskAssignRule){
        // 获得规则
        List<BpmTaskAssignRule> rules = Collections.emptyList();
        BpmnModel model = null;
        if (StrUtil.isNotEmpty(bpmTaskAssignRule.getModelId())) {
            //数据库规则
            rules = bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleList(bpmTaskAssignRule);
            //流程数据规则
            model = bpmModelService.getBpmnModel(bpmTaskAssignRule.getModelId());
        } else if (StrUtil.isNotEmpty(bpmTaskAssignRule.getProcessDefinitionId())) {
            //数据库规则
            rules = bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleList(bpmTaskAssignRule);
            //流程数据规则
            model = bpmProcessDefinitionService.getBpmnModel(bpmTaskAssignRule.getProcessDefinitionId());
        }
        if (model == null) {
            return Collections.emptyList();
        }
//        // 获得用户任务，只有用户任务才可以设置分配规则
        List<UserTask> userTasks = BpmnModelUtils.getBpmnModelElements(model, UserTask.class);
        if (CollUtil.isEmpty(userTasks)) {
            return Collections.emptyList();
        }
//        // 转换数据
        Map<String,BpmTaskAssignRule> ruleMap = CollectionUtils.convertMap(rules, BpmTaskAssignRule::getTaskDefinitionKey);
        return CollectionUtils.convertList(userTasks, task -> {
            BpmTaskAssignRule respVO = ruleMap.get(task.getId());
            if (respVO == null) {
                respVO = new BpmTaskAssignRule();
                respVO.setTaskDefinitionKey(task.getId());
            }
            setTaskAssignRuleOptionName(respVO.getType(), respVO);

            respVO.setTaskDefinitionName(task.getName());
            return respVO;
        });
    }

    /**
     * 新增Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    @Override
    public int insertBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule){
        bpmTaskAssignRule.setCreateTime(DateUtils.getNowDate());
        return bpmTaskAssignRuleMapper.insertBpmTaskAssignRule(bpmTaskAssignRule);
    }

    /**
     * 修改Bpm 任务规则
     *
     * @param bpmTaskAssignRule Bpm 任务规则
     * @return 结果
     */
    @Override
    public int updateBpmTaskAssignRule(BpmTaskAssignRule bpmTaskAssignRule){
        bpmTaskAssignRule.setUpdateTime(DateUtils.getNowDate());
        return bpmTaskAssignRuleMapper.updateBpmTaskAssignRule(bpmTaskAssignRule);
    }

    /**
     * 批量删除Bpm 任务规则
     *
     * @param ids 需要删除的Bpm 任务规则主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskAssignRuleByIds(Long[] ids){
        return bpmTaskAssignRuleMapper.deleteBpmTaskAssignRuleByIds(ids);
    }

    /**
     * 删除Bpm 任务规则信息
     *
     * @param id Bpm 任务规则主键
     * @return 结果
     */
    @Override
    public int deleteBpmTaskAssignRuleById(Long id){
        return bpmTaskAssignRuleMapper.deleteBpmTaskAssignRuleById(id);
    }

    @Override
    public int createTaskAssignRule(BpmTaskAssignRule reqVO){
        if (StringUtils.isNull(reqVO.getTaskDefinitionKey())) {
            throw new ServiceException("流程任务定义的编号不能为空！");
        }
        // 校验参数
        String options = validTaskAssignRuleOptions(reqVO.getType(), reqVO.getOptionIds(), reqVO.getOptions());
        // 校验是否已经配置
        BpmTaskAssignRule existRule =
                bpmTaskAssignRuleMapper.selectListByModelIdAndTaskDefinitionKey(reqVO.getModelId(), reqVO.getTaskDefinitionKey());
        if (existRule != null) {
            throw new ServiceException(String.format("流程(%s) 的任务(%s) 已经存在分配规则", reqVO.getModelId(), reqVO.getTaskDefinitionKey()), HttpStatus.ERROR);
        }
        reqVO.setOptions(options);
//      空字符串  用于标识属于流程模型，而不属于流程定义
        reqVO.setProcessDefinitionId("0");
        // 存储
        return bpmTaskAssignRuleMapper.insertBpmTaskAssignRule(reqVO);
    }

    @Override
    public int updateTaskAssignRule(BpmTaskAssignRule reqVO){
        //编辑不能修改
        reqVO.setTaskDefinitionKey(null);
        // 校验参数
        String options = validTaskAssignRuleOptions(reqVO.getType(), reqVO.getOptionIds(), reqVO.getOptions());
        // 校验是否存在
        BpmTaskAssignRule existRule = bpmTaskAssignRuleMapper.selectBpmTaskAssignRuleById(reqVO.getId());
        if (existRule == null) {
            throw new ServiceException("流程任务分配规则不存在", HttpStatus.ERROR);
        }
        // 只允许修改流程模型的规则
        if (!Objects.equals("0", existRule.getProcessDefinitionId())) {
            throw new ServiceException("只有流程模型的任务分配规则，才允许被修改", HttpStatus.ERROR);
        }
        //参数设置
        reqVO.setOptions(options);
        // 执行更新
        return bpmTaskAssignRuleMapper.updateBpmTaskAssignRule(reqVO);
    }
    private void setTaskAssignRuleOptionName(Integer type, BpmTaskAssignRule taskAssignRule){
        if (StringUtils.isEmpty(taskAssignRule.getOptions())){
            return;
        }
        List<String> list = Arrays.asList(taskAssignRule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.ROLE.getType())) {
            //角色id名称设置
            List<SysRole> sysRoles = roleService.selectBatchIds(ids);
            List<String> roleNames = sysRoles.stream()
                    .map(SysRole::getRoleName)
                    .collect(Collectors.toList());
            taskAssignRule.setOptionName(Joiner.on(",").join(roleNames));
        } else if (ObjectUtils.equalsAny(type, BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
                BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType())) {
            //部门名称设置
            List<SysDept> sysDepts = deptService.selectBatchIds(ids);
            List<String> deptName = sysDepts.stream()
                    .map(SysDept::getDeptName)
                    .collect(Collectors.toList());
            taskAssignRule.setOptionName(Joiner.on(",").join(deptName));
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.POST.getType())) {
            //校验岗位id是否存在
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER.getType())) {
            //校验用户id是否存在
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER_GROUP.getType())) {
            //校验用户组id是否存在
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.SCRIPT.getType())) {
//            字典校验
            String value = dictDataService.selectDictLabel("bpm_task_assign_script", taskAssignRule.getOptions());
            taskAssignRule.setOptionName(value);
        }
    }
    private String validTaskAssignRuleOptions(Integer type, List<Long> optionIds, String option){
        if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.ROLE.getType())) {
            //校验角色id是否存在
            roleService.validateRoleList(optionIds);
        } else if (ObjectUtils.equalsAny(type, BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
                BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType())) {
            //校验部门id是否存在
            deptService.validateDeptList(optionIds);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.POST.getType())) {
            //校验岗位id是否存在
            postService.validPostList(optionIds);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER.getType())) {
            //校验用户id是否存在
            userService.validateUserList(optionIds);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER_GROUP.getType())) {
            //校验用户组id是否存在
            userGroupService.validUserGroups(optionIds);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.SCRIPT.getType())) {
//            字典校验
            String value = dictDataService.selectDictLabel("bpm_task_assign_script", option);
            if (StringUtils.isEmpty(value)) {
                throw new ServiceException(String.format("type为【%s】的类型不存在", option), HttpStatus.ERROR);
            }
            return option;
        } else {
            throw new ServiceException(String.format("未知的规则类型【%s】", type), HttpStatus.ERROR);
        }
        String result = Joiner.on(",").join(optionIds);
        return  result;
    }
}
