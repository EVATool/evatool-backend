package com.evatool.impact.domain.event.json;

import com.evatool.impact.domain.entity.Impact;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ImpactJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private double value;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String stakeholderId;

    @Getter
    @Setter
    private String dimensionId;

    @Getter
    @Setter
    private String analysisId;

    public boolean equalsEntity(Impact that) {
        if (that == null) return false;
        return Objects.equals(this.id, that.getId().toString())
                && Double.compare(this.value, that.getValue()) == 0
                && Objects.equals(this.description, that.getDescription())
                && Objects.equals(this.stakeholderId, that.getStakeholder().getId().toString())
                && Objects.equals(this.dimensionId, that.getDimension().getId().toString())
                && Objects.equals(this.analysisId, that.getAnalysis().getId().toString());
    }
}
