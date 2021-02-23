package com.evatool.global.event.requirements;

import com.evatool.requirements.dto.RequirementDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RequirementUpdatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public RequirementUpdatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }

}
