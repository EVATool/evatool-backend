package com.evatool.impact.domain.event.json;

import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.impact.domain.entity.Value;
import com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ImpactValueEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public ImpactValueEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishValueCreated(final Value value) {
        logger.info("Preparing to publish create Value event");
        var valueJson = ImpactValueJsonMapper.toJson(value);
        var valueCreatedEvent = new ValueCreatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueCreatedEvent);
        logger.info("Create value event published");
    }

    public void publishValueDeleted(final Value value) {
        logger.info("Preparing to publish delete value event");
        var valueJson = ImpactValueJsonMapper.toJson(value);
        var valueDeletedEvent = new ValueDeletedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueDeletedEvent);
        logger.info("Delete value event published");
    }

    public void publishValueUpdated(final Value value) {
        logger.info("Preparing to publish update value event");
        var valueJson = ImpactValueJsonMapper.toJson(value);
        var valueUpdatedEvent = new ValueUpdatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueUpdatedEvent);
        logger.info("Update value event published");
    }
}
