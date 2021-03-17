package com.evatool.variants.domain.entities;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "VAR_ANALYSIS")
@Getter
public class VariantsAnalysis extends RepresentationModel<VariantsAnalysis> {


    @Id
    @Type(type = "uuid-char")
    private UUID analysisId;

    public VariantsAnalysis(UUID id){
        this.analysisId = id;
    }

    public VariantsAnalysis(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VariantsAnalysis that = (VariantsAnalysis) o;
        return Objects.equals(analysisId, that.analysisId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), analysisId);
    }
}
