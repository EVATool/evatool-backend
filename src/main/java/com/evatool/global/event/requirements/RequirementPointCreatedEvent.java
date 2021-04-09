package com.evatool.global.event.requirements;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementPointCreatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementPointCreatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
