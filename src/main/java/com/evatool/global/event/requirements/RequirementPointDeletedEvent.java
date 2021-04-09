package com.evatool.global.event.requirements;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementPointDeletedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementPointDeletedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
