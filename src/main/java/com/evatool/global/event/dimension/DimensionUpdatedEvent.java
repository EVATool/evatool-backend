package com.evatool.global.event.dimension;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class DimensionUpdatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public DimensionUpdatedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
