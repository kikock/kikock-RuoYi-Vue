package com.ruoyi.flowable.service.task.impl;

import com.ruoyi.flowable.domain.task.vo.BpmActivityRespVO;
import com.ruoyi.flowable.service.task.IBpmActivityService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BpmActivityServiceImpl implements IBpmActivityService {
    @Resource
    private HistoryService historyService;
    @Override
    public List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId) {
        List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return convertList(activityList);
    }

    public List<BpmActivityRespVO> convertList(List<HistoricActivityInstance> list) {
        if ( list == null ) {
            return null;
        }

        List<BpmActivityRespVO> list1 = new ArrayList<BpmActivityRespVO>( list.size() );
        for ( HistoricActivityInstance historicActivityInstance : list ) {
            list1.add( convert( historicActivityInstance ) );
        }

        return list1;
    }
    public BpmActivityRespVO convert(HistoricActivityInstance bean) {
        if ( bean == null ) {
            return null;
        }

        BpmActivityRespVO bpmActivityRespVO = new BpmActivityRespVO();

        bpmActivityRespVO.setKey( bean.getActivityId() );
        bpmActivityRespVO.setType( bean.getActivityType() );
        bpmActivityRespVO.setStartTime(  bean.getStartTime() );
        bpmActivityRespVO.setEndTime( bean.getEndTime() );
        bpmActivityRespVO.setTaskId( bean.getTaskId() );

        return bpmActivityRespVO;
    }


    @Override
    public List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId) {
        return historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
    }
}
