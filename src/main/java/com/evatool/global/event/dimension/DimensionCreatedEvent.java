package com.evatool.global.event.dimension;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class DimensionCreatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public DimensionCreatedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
