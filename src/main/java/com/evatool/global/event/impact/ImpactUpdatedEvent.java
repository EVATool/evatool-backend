package com.evatool.global.event.impact;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ImpactUpdatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public ImpactUpdatedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
