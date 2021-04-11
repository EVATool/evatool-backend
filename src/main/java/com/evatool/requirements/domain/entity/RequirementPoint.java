
package com.evatool.requirements.domain.entity;


import com.evatool.requirements.common.exceptions.IllegalDtoValueException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "REQ_RequirementPoint")
@Table(name = "REQ_RequirementPoint")
public class RequirementPoint {

    @Id
    @Type(type= "uuid-char")
    @GeneratedValue(generator = "SuperEntityUuidGenerator")
    @GenericGenerator(name = "SuperEntityUuidGenerator", strategy = "com.evatool.requirements.domain.entity.SuperEntityUuidGenerator")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @ManyToOne
    private RequirementsImpact requirementsImpact;
    private Double points;

    public RequirementPoint() {
    }

    public RequirementPoint(RequirementsImpact requirementsImpact, Double points) {
        this.requirementsImpact = requirementsImpact;
        this.points = points;
    }

    public RequirementsImpact getRequirementsImpact() {
        return requirementsImpact;
    }

    public void setRequirementsImpact(RequirementsImpact requirementsImpact) {
        if (requirementsImpact == null) {
            throw new IllegalArgumentException("RequirementsImpacts cannot be null.");
        }

        this.requirementsImpact = requirementsImpact;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) throws IllegalArgumentException {
        if (points < -1 || points > 1) {
            throw new IllegalDtoValueException("ImpactValue must be in range [-1, 1]");
        }

        this.points = points;
    }

    public UUID getId() {
        return id;
    }

}
