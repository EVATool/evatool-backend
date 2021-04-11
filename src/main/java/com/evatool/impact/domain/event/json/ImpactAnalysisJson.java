package com.evatool.impact.domain.event.json;

import com.evatool.impact.domain.entity.ImpactAnalysis;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@ToString
public class ImpactAnalysisJson {

    @Getter
    @Setter
    private UUID analysisId;

    public boolean equalsEntity(ImpactAnalysis that) {
        if (that == null) return false;
        return Objects.equals(this.analysisId, that.getId());
    }
}
