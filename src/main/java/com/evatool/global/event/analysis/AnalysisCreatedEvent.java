package com.evatool.global.event.analysis;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AnalysisCreatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public AnalysisCreatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
