package com.evatool.analysis.domain.events;

import com.evatool.analysis.domain.events.json.ValueJsonMapper;
import com.evatool.analysis.domain.model.Value;
import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ValueEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ValueEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public ValueEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishValueCreated(final Value value) {
        logger.info("Preparing to publish create ImpactValue event");
        var valueJson = ValueJsonMapper.toJson(value);
        var valueCreatedEvent = new ValueCreatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueCreatedEvent);
        logger.info("Create value event published");
    }

    public void publishValueDeleted(final Value value) {
        logger.info("Preparing to publish delete value event");
        var valueJson = ValueJsonMapper.toJson(value);
        var valueDeletedEvent = new ValueDeletedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueDeletedEvent);
        logger.info("Delete value event published");
    }

    public void publishValueUpdated(final Value value) {
        logger.info("Preparing to publish update value event");
        var valueJson = ValueJsonMapper.toJson(value);
        var valueUpdatedEvent = new ValueUpdatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueUpdatedEvent);
        logger.info("Update value event published");
    }
}