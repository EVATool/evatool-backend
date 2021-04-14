package com.evatool.analysis.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AnalysisImpact {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    private Float impactValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisImpact impact = (AnalysisImpact) o;
        return Objects.equals(id, impact.id) && Objects.equals(impactValue, impact.impactValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, impactValue);
    }
}
