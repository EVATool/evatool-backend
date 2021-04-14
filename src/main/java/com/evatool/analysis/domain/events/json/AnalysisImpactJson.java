package com.evatool.analysis.domain.events.json;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class AnalysisImpactJson {

    private UUID id;
    private Float value;
    private UUID stakeholderId;
}

