package com.ruoyi.flowable.service.definition.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.vo.SelectMoreVo;
import com.ruoyi.common.core.domain.vo.SysUserSimpleVo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ObjectUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.collection.CollectionUtils;
import com.ruoyi.flowable.domain.definition.BpmTaskAssignRule;
import com.ruoyi.flowable.domain.definition.BpmUserGroup;
import com.ruoyi.flowable.enums.BpmTaskAssignRuleTypeEnum;
import com.ruoyi.flowable.framework.code.behavior.script.IBpmTaskAssignScript;
import com.ruoyi.flowable.framework.utils.BpmnModelUtils;
import com.ruoyi.flowable.mapper.definition.BpmTaskAssignRuleMapper;
import com.ruoyi.flowable.service.definition.IBpmModelService;
import com.ruoyi.flowable.service.definition.IBpmProcessDefinitionService;
import com.ruoyi.flowable.service.definition.IBpmTaskAssignRuleService;
import com.ruoyi.flowable.service.definition.IBpmUserGroupService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.text.CharSequenceUtil.format;
import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;

/**
 * Bpm 任务规则Service业务层处理
 *
 * @author kikock
 * @date 2023-12-22
 */
@Slf4j
@Service
public class BpmTaskAssignRuleServiceImpl implements IBpmTaskAssignRuleService{
    @Resource
    private BpmTaskAssignRuleMapper bpmTaskAssignRuleMapper;
    @Autowired
    @Lazy
    private IBpmModelService bpmModelService;
    @Autowired
    @Lazy
    private IBpmProcessDefinitionService bpmProcessDefinitionService;

    @Autowired
    @Lazy
    private RuntimeService runtimeService;

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
     * 任务分配脚本
     */
    private Map<Long,IBpmTaskAssignScript> scriptMap = Collections.emptyMap();

    @Resource
    public void setScripts(List<IBpmTaskAssignScript> scripts) {
        this.scriptMap = convertMap(scripts, script -> script.getEnum().getId());
    }
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
        Map<String,BpmTaskAssignRule> ruleMap = convertMap(rules, BpmTaskAssignRule::getTaskDefinitionKey);
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

    /**
     * 校验流程模型的任务分配规则全部都配置了
     * 目的：如果有规则未配置，会导致流程任务找不到负责人，进而流程无法进行下去！
     *
     * @param id 流程模型编号
     */
    @Override
    public void checkTaskAssignRuleAllConfig(String id){
        BpmTaskAssignRule bpmTaskAssignRule = new BpmTaskAssignRule();
        bpmTaskAssignRule.setModelId(id);
        // 一个用户任务都没配置，所以无需配置规则
        List<BpmTaskAssignRule> taskAssignRules = selectBpmTaskAssignRuleList(bpmTaskAssignRule);
        if (CollUtil.isEmpty(taskAssignRules)) {
            return;
        }
        // 获得规则
        List<BpmTaskAssignRule> rules = Collections.emptyList();
        // 校验未配置规则的任务
        taskAssignRules.forEach(rule -> {
            if (rule.getType()!= 50 && CollUtil.isEmpty(rule.getSelectMoreVos())) {
                throw new ServiceException(String.format("部署流程失败，原因：用户任务(%s)未配置分配规则，请点击【修改流程】按钮进行配置!", rule.getTaskDefinitionName()), HttpStatus.ERROR);
            }
            if(rule.getType()== 50 && StringUtils.isEmpty(rule.getOptionName())){
                throw new ServiceException(String.format("部署流程失败，原因：用户任务(%s)未配置分配规则，请点击【修改流程】按钮进行配置!", rule.getTaskDefinitionName()), HttpStatus.ERROR);
            }
        });

    }

    @Override
    public boolean isTaskAssignRulesEquals(String modelId, String processDefinitionId){
        // 过滤掉流程模型不需要的规则
        BpmTaskAssignRule bpmTaskAssignRuleModel = new BpmTaskAssignRule();
        bpmTaskAssignRuleModel.setModelId(modelId);
        List<BpmTaskAssignRule> modelRules = selectBpmTaskAssignRuleList(bpmTaskAssignRuleModel);
        BpmTaskAssignRule bpmTaskAssignRuleProcessDefinition = new BpmTaskAssignRule();

        bpmTaskAssignRuleProcessDefinition.setProcessDefinitionId(processDefinitionId);
        List<BpmTaskAssignRule> processDefinitionRules = selectBpmTaskAssignRuleList(bpmTaskAssignRuleModel);
        if (modelRules.size() != processDefinitionRules.size()) {
            return false;
        }

        // 遍历，匹配对应的规则
        Map<String,BpmTaskAssignRule> processInstanceRuleMap =
                convertMap(processDefinitionRules, BpmTaskAssignRule::getTaskDefinitionKey);

        for (BpmTaskAssignRule modelRule : modelRules) {
            BpmTaskAssignRule processInstanceRule = processInstanceRuleMap.get(modelRule.getTaskDefinitionKey());
            if (processInstanceRule == null) {
                return false;
            }
            if (!ObjectUtil.equals(modelRule.getType(), processInstanceRule.getType()) || !ObjectUtil.equal(
                    modelRule.getOptions(), processInstanceRule.getOptions())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void copyTaskAssignRules(String fromModelId, String toProcessDefinitionId){
        BpmTaskAssignRule bpmTaskAssignRule = new BpmTaskAssignRule();
        bpmTaskAssignRule.setModelId(fromModelId);
        List<BpmTaskAssignRule> rules = selectBpmTaskAssignRuleList(bpmTaskAssignRule);
        if (CollUtil.isEmpty(rules)) {
            return;
        }
        rules.forEach(rule -> {
            rule.setProcessDefinitionId(toProcessDefinitionId);
            rule.setId(null);
        });
        bpmTaskAssignRuleMapper.insertBatchBpmTaskAssignRule(rules);
    }
    @Override
    public Set<Long> calculateTaskCandidateUsers(String name, String processDefinitionId, String taskDefinitionKey,String processInstanceId){
        List<BpmTaskAssignRule> taskRules = bpmTaskAssignRuleMapper.getTaskAssignRuleListByProcessDefinitionId(
                processDefinitionId, taskDefinitionKey);
        if (CollUtil.isEmpty(taskRules)) {
            throw new FlowableException(format("流程任务({}/{}/{}) 找不到符合的任务规则",
                    name, processDefinitionId, taskDefinitionKey));
        }
        if (taskRules.size() > 1) {
            throw new FlowableException(format("流程任务({}/{}/{}) 找到过多任务规则({})",
                    name, processDefinitionId, taskDefinitionKey));
        }
        BpmTaskAssignRule bpmTaskAssignRule = taskRules.get(0);
        return calculateTaskCandidateUsers(name, processDefinitionId, taskDefinitionKey, bpmTaskAssignRule,processInstanceId);
    }
    @VisibleForTesting
    Set<Long> calculateTaskCandidateUsers(String name, String processDefinitionId, String taskDefinitionKey, BpmTaskAssignRule rule ,String processInstanceId){
        Set<Long> assigneeUserIds = null;
        if (Objects.equals(BpmTaskAssignRuleTypeEnum.ROLE.getType(), rule.getType())) {
//            角色
            assigneeUserIds = calculateTaskCandidateUsersByRole(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(), rule.getType())) {
            //部门
            assigneeUserIds = calculateTaskCandidateUsersByDeptMember(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByDeptLeader(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.POST.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByPost(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByUser(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER_GROUP.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByUserGroup(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.SCRIPT.getType(), rule.getType())) {
            assigneeUserIds =calculateTaskCandidateUsersByScript(processInstanceId,rule);
        }
        // 移除被禁用的用户
        removeDisableUsers(assigneeUserIds);
        // 如果候选人为空，抛出异常
        if (CollUtil.isEmpty(assigneeUserIds)) {
            log.error("[calculateTaskCandidateUsers][流程任务({}/{}/{}) 任务规则({}) 找不到候选人]", name,
                    processDefinitionId, taskDefinitionKey, JSONUtil.toJsonStr(rule));
            throw new ServiceException("操作失败，原因：找不到任务的审批人！" ,HttpStatus.ERROR);
        }
        return assigneeUserIds;
    }

    private Set<Long> calculateTaskCandidateUsersByRole(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        Set<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toSet());
        return roleService.selectUsersBatchIds(ids);
    }

    private Set<Long> calculateTaskCandidateUsersByDeptMember(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<SysUserSimpleVo> userSimpleVos = userService.selectBatchDeptIds(ids);
        return userSimpleVos.stream().map(SysUserSimpleVo::getId).collect(Collectors.toSet());
    }

    private Set<Long> calculateTaskCandidateUsersByDeptLeader(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<SysDept> sysDepts = deptService.selectBatchIds(ids);
        return sysDepts.stream().map(SysDept::getLeader).collect(Collectors.toSet());
    }

    private Set<Long> calculateTaskCandidateUsersByPost(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<SysUserSimpleVo> userSimpleVos = userService.selectBatchPostIds(ids);
        return userSimpleVos.stream().map(SysUserSimpleVo::getId).collect(Collectors.toSet());
    }

    private Set<Long> calculateTaskCandidateUsersByUser(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        return list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toSet());
    }

    private Set<Long> calculateTaskCandidateUsersByUserGroup(BpmTaskAssignRule rule){
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        Set<Long> userIds = new HashSet<>();
        List<BpmUserGroup> bpmUserGroups = userGroupService.selectBatchIds(ids);
        bpmUserGroups.forEach(group -> {
            String memberUserIds = group.getMemberUserIds();
            List<String> ids1 = Arrays.asList(group.getMemberUserIds().split(","));
            List<Long> ids2 = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            userIds.addAll(ids2);
        });
        return userIds;
    }
    private Set<Long> calculateTaskCandidateUsersByScript(String processDefinitionId, BpmTaskAssignRule rule) {
        // 获得对应的脚本
        List<String> list = Arrays.asList(rule.getOptions().split(","));
        List<IBpmTaskAssignScript> scripts = new ArrayList<>(list.size());
        list.forEach(id -> {
            IBpmTaskAssignScript script = scriptMap.get(Long.valueOf(id));
            if (script == null) {
                throw new ServiceException("操作失败，原因：操作失败，原因：任务分配脚本不存在！" ,HttpStatus.ERROR);
            }

            scripts.add(script);
        });
        // 逐个计算任务
        Set<Long> userIds = new HashSet<>();
        scripts.forEach(script -> CollUtil.addAll(userIds, script.calculateTaskCandidateUsers(processDefinitionId)));
        return userIds;
    }

    @VisibleForTesting
    void removeDisableUsers(Set<Long> assigneeUserIds){
        if (CollUtil.isEmpty(assigneeUserIds)) {
            return;
        }
//        再查询一次  移除禁用或删除用户
//        List<SysUserSimpleVo> userSimpleVos = userService.selectBatchIds((List<Long>) assigneeUserIds);
//         userSimpleVos.stream().map(SysUserSimpleVo::getId).collect(Collectors.toSet());
    }

    private void setTaskAssignRuleOptionName(Integer type, BpmTaskAssignRule taskAssignRule){
        if (StringUtils.isEmpty(taskAssignRule.getOptions())) {
            return;
        }
        List<String> list = Arrays.asList(taskAssignRule.getOptions().split(","));
        List<Long> ids = list.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.ROLE.getType())) {
            //角色id名称设置
            List<SysRole> sysRoles = roleService.selectBatchIds(ids);
            //回显内容处理
            List<SelectMoreVo> selectMoreVos = sysRoles.stream()
                    .map(item -> {
                        SelectMoreVo selectMoreVo = new SelectMoreVo();
                        selectMoreVo.setId(item.getRoleId());
                        selectMoreVo.setName(item.getRoleName());
                        return selectMoreVo;
                    })
                    .collect(Collectors.toList());
            taskAssignRule.setSelectMoreVos(selectMoreVos);
        } else if (ObjectUtils.equalsAny(type, BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
                BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType())) {
            //部门名称和id设置
            List<SysDept> sysDepts = deptService.selectBatchIds(ids);
            //下拉回显内容处理
            List<SelectMoreVo> selectMoreVos = sysDepts.stream()
                    .map(item -> {
                        SelectMoreVo selectMoreVo = new SelectMoreVo();
                        selectMoreVo.setId(item.getDeptId());
                        selectMoreVo.setName(item.getDeptName());
                        return selectMoreVo;
                    })
                    .collect(Collectors.toList());
            taskAssignRule.setSelectMoreVos(selectMoreVos);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.POST.getType())) {
            //设置岗位名称
            List<SysPost> sysPosts = postService.selectBatchIds(ids);
            //下拉回显内容处理
            List<SelectMoreVo> selectMoreVos = sysPosts.stream()
                    .map(item -> {
                        SelectMoreVo selectMoreVo = new SelectMoreVo();
                        selectMoreVo.setId(item.getPostId());
                        selectMoreVo.setName(item.getPostName());
                        return selectMoreVo;
                    })
                    .collect(Collectors.toList());
            taskAssignRule.setSelectMoreVos(selectMoreVos);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER.getType())) {
            //设置用户名称
            List<SysUserSimpleVo> sysUserSimpleVos = userService.selectBatchIds(ids);
            //下拉回显内容处理
            List<SelectMoreVo> selectMoreVos = sysUserSimpleVos.stream()
                    .map(item -> {
                        SelectMoreVo selectMoreVo = new SelectMoreVo();
                        selectMoreVo.setId(item.getId());
                        selectMoreVo.setName(item.getName());
                        return selectMoreVo;
                    })
                    .collect(Collectors.toList());
            taskAssignRule.setSelectMoreVos(selectMoreVos);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER_GROUP.getType())) {
            //用户组名称设
            List<BpmUserGroup> bpmUserGroups = userGroupService.selectBatchIds(ids);
            //下拉回显内容处理
            List<SelectMoreVo> selectMoreVos = bpmUserGroups.stream()
                    .map(item -> {
                        SelectMoreVo selectMoreVo = new SelectMoreVo();
                        selectMoreVo.setId(item.getId());
                        selectMoreVo.setName(item.getName());
                        return selectMoreVo;
                    })
                    .collect(Collectors.toList());
            taskAssignRule.setSelectMoreVos(selectMoreVos);
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
        //返回 逗号分隔字符串
        String result = Joiner.on(",").join(optionIds);
        return result;
    }
}
