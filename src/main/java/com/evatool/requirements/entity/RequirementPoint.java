
package com.evatool.requirements.entity;


import com.evatool.requirements.error.exceptions.IllegalDtoValueExcpetion;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "REQ_RequirementPoint")
public class RequirementPoint {

    @Id
    private final UUID id = UUID.randomUUID();
    @ManyToOne
    private RequirementsImpact requirementsImpact;
    @ManyToOne
    private Requirement requirement;
    private float points;

    public RequirementPoint() {
    }

    public RequirementPoint(RequirementsImpact requirementsImpact, Requirement requirement, float points) {
        this.requirementsImpact = requirementsImpact;
        this.requirement = requirement;
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

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) throws IllegalArgumentException {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement cannot be null.");
        }
        this.requirement = requirement;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(Float points) throws IllegalArgumentException {
        if (points < -1 || points > 1) {
            throw new IllegalDtoValueExcpetion("Value must be in range [-1, 1]");
        }

        this.points = points;
    }

    public UUID getId() {
        return id;
    }

}
