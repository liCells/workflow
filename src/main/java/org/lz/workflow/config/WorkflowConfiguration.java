package org.lz.workflow.config;

import org.lz.workflow.helper.LoadNodeMapHelper;
import org.lz.workflow.service.FlowDesignService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * workflow config
 *
 * @author lz
 */
@MapperScan("org.lz.workflow.mapper")
@ComponentScan("org.lz.workflow")
@Component
public class WorkflowConfiguration {
    static boolean cacheFlowStructure;
    static boolean sync;

    public WorkflowConfiguration(
            @Value("${workflow.cache.structure:false}") boolean cacheFlowStructure,
            @Value("${workflow.cache.sync:true}") boolean lazy, FlowDesignService flowDesignService) {
        WorkflowConfiguration.cacheFlowStructure = cacheFlowStructure;
        WorkflowConfiguration.sync = lazy;

        if (WorkflowConfiguration.sync) {
            if (WorkflowConfiguration.cacheFlowStructure) {
                new LoadNodeMapHelper(flowDesignService).load();
            }
        } else {
            if (WorkflowConfiguration.cacheFlowStructure) {
                CompletableFuture.runAsync(() -> new LoadNodeMapHelper(flowDesignService).load());
            }
        }
    }
}
