package com.evatool.global.event.requirements;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementDeletedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementDeletedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }


}
