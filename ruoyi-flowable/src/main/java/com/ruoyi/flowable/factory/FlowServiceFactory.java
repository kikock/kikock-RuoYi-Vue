package com.ruoyi.flowable.factory;


import jakarta.annotation.Resource;
import lombok.Getter;
import org.flowable.engine.*;
import org.flowable.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;


/**
 * flowable 引擎注入封装
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Component
public class FlowServiceFactory{
    @Resource
    protected RepositoryService repositoryService;

    @Resource
    protected RuntimeService runtimeService;

    @Resource
    protected IdentityService identityService;

    @Resource
    protected TaskService taskService;

    @Resource
    protected FormService formService;

    @Resource
    protected HistoryService historyService;

    @Resource
    protected ManagementService managementService;

    @Qualifier("processEngine")
    @Resource
    protected ProcessEngine processEngine;

    public RepositoryService getRepositoryService(){
        return repositoryService;
    }


    public RuntimeService getRuntimeService(){
        return runtimeService;
    }

    public IdentityService getIdentityService(){
        return identityService;
    }


    public TaskService getTaskService(){
        return taskService;
    }


    public FormService getFormService(){
        return formService;
    }


    public HistoryService getHistoryService(){
        return historyService;
    }


    public ManagementService getManagementService(){
        return managementService;
    }


    public ProcessEngine getProcessEngine(){
        return processEngine;
    }

}
