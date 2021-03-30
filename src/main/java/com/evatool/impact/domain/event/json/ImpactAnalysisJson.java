package com.evatool.impact.domain.event.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
public class ImpactAnalysisJson {

    @Getter
    @Setter
    private UUID analysisId;
}
