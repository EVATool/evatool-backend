package com.evatool.global.event.requirements;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementPointUpdatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementPointUpdatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
