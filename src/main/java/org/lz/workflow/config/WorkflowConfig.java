package org.lz.workflow.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * workflow config
 *
 * @author lz
 */
@MapperScan("org.lz.workflow.mapper")
@ComponentScan("org.lz.workflow")
public class WorkflowConfig {
}
