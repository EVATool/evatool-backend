package com.evatool.global.event.requirements;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementUpdatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementUpdatedEvent(final String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }

}
