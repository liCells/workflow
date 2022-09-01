package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.mapper.FlowTaskMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author lz
 */
@Service
public class FlowTaskService extends ServiceImpl<FlowTaskMapper, RunningTask> {
    private final FlowCommonService flowCommonService;

    public FlowTaskService(FlowCommonService flowCommonService) {
        this.flowCommonService = flowCommonService;
    }

    @Transactional
    @EventListener(StartFlowEvent.class)
    public void saveTask(StartFlowEvent startFlowEvent) {
        Long id = flowCommonService.getIdAndIncr(FlowCommonEnum.TASK_KEY);
        final RunningTask task = new RunningTask();
        task.setId(id);
        task.setFlowId(startFlowEvent.getFlowId());
        task.setFlowSymbol(startFlowEvent.getFlowSymbol());
        task.setStartTime(LocalDate.now());

        // TODO parse flow design and save to task
        saveTask(task);
    }

    private void saveTask(RunningTask task) {
        save(task);
    }
}
