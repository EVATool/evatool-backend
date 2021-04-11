package com.evatool.impact.domain.event.json;

import com.evatool.impact.domain.entity.ImpactValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@ToString
public class ImpactValueJson {

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
    private UUID analysisId;

    public boolean equalsEntity(ImpactValue that) {
        if (that == null) return false;
        return Objects.equals(this.id, that.getId())
                && Objects.equals(this.name, that.getName())
                && Objects.equals(this.type, that.getType())
                && Objects.equals(this.description, that.getDescription())
                && Objects.equals(this.analysisId, that.getAnalysis().getId());
    }
}
