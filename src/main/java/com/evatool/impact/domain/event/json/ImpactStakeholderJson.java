package com.evatool.impact.domain.event.json;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class ImpactStakeholderJson {

    @Getter
    @Setter
    private UUID stakeholderId;

    @Getter
    @Setter
    private String stakeholderName;
}
