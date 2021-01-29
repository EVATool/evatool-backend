package com.evatool.impact.persistence.event.stakeholder;

import com.evatool.impact.persistence.entity.Stakeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StakeholderInsertedEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void onStakeholderInserted(final Stakeholder stakeholder) {
        var stakeholderInsertEvent = new StakeholderInsertedEvent(this, stakeholder);
        applicationEventPublisher.publishEvent(stakeholderInsertEvent);
    }
}