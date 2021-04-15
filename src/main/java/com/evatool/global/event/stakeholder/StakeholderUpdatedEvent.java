package com.evatool.global.event.stakeholder;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class StakeholderUpdatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public StakeholderUpdatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
