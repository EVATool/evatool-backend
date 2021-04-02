package com.evatool.analysis.domain.events.json;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class ValueJson {

    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String analysisId;
}
