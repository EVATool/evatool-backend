package com.evatool.impact.domain.event.json;

import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.impact.domain.entity.ImpactValue;
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

    public void publishValueCreated(final ImpactValue impactValue) {
        logger.info("Preparing to publish create ImpactValue event");
        var valueJson = ImpactValueJsonMapper.toJson(impactValue);
        var valueCreatedEvent = new ValueCreatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueCreatedEvent);
        logger.info("Create impactValue event published");
    }

    public void publishValueDeleted(final ImpactValue impactValue) {
        logger.info("Preparing to publish delete impactValue event");
        var valueJson = ImpactValueJsonMapper.toJson(impactValue);
        var valueDeletedEvent = new ValueDeletedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueDeletedEvent);
        logger.info("Delete impactValue event published");
    }

    public void publishValueUpdated(final ImpactValue impactValue) {
        logger.info("Preparing to publish update impactValue event");
        var valueJson = ImpactValueJsonMapper.toJson(impactValue);
        var valueUpdatedEvent = new ValueUpdatedEvent(this, valueJson);
        applicationEventPublisher.publishEvent(valueUpdatedEvent);
        logger.info("Update impactValue event published");
    }
}
