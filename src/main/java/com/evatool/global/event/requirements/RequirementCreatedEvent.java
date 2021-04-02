package com.evatool.global.event.requirements;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementCreatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementCreatedEvent(final String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
