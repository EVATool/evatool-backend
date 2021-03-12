package com.evatool.global.event.impact;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ImpactDeletedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public ImpactDeletedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
