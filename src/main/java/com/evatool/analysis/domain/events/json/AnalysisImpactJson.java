package com.evatool.analysis.domain.events.json;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@Getter
@Setter
public class AnalysisImpactJson {

    private UUID id;
    private Double value;
    private ImpactStakeholder stakeholder;
}

class ImpactStakeholder{
    private UUID id;
}
