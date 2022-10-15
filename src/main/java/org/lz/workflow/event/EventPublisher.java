package org.lz.workflow.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author lz
 */
@Component
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void setFlowEvent(FlowEvent flowEvent) {
        publisher.publishEvent(flowEvent);
    }
}
