package com.evatool.global.event.stakeholder;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class StakeholderCreatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public StakeholderCreatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
