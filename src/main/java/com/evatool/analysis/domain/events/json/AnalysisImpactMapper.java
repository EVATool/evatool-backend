package com.evatool.analysis.domain.events.json;

import com.evatool.analysis.domain.model.AnalysisImpact;

public class AnalysisImpactMapper {

    public AnalysisImpact map(AnalysisImpactJson json){
        return new AnalysisImpact();
    }
}
