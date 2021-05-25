package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.awt.*;

import static com.evatool.common.validation.OverwriteMeritValidation.validateOverwriteMerit;

@Entity(name = "requirement_delta")
@Table(name = "requirement_delta")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequirementDelta extends SuperEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(RequirementDelta.class);

    @Getter
    @Column(name = "overwrite_merit", nullable = false)
    private Float overwriteMerit;

    @Getter
    @ManyToOne(optional = false)
    private Impact impact;

    @Getter
    @ManyToOne(optional = false)
    private Requirement requirement;

    public RequirementDelta(Float overwriteMerit, Impact impact, Requirement requirement) {
        super();
        logger.debug("Constructor");
        setImpact(impact);
        setOverwriteMerit(overwriteMerit);
        setRequirement(requirement);
    }

    private RequirementDelta() {

    }

    @PrePersist
    @PreUpdate
    @PreRemove
    void prePersistUpdateRemove() {
        logger.debug("Pre Persist/Pre Update/Pre Remove");
        FindByAnalysis.super.updateAnalysisLastUpdated();
    }

    @PostPersist
    void postPersist() {
        logger.debug("Post Persist");
        impact.getRequirementDeltas().add(this);
        requirement.getRequirementDeltas().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        impact.getRequirementDeltas().remove(this);
        requirement.getRequirementDeltas().remove(this);
    }

    public void setOverwriteMerit(Float overwriteMerit) {
        logger.debug("Set Overwrite Merit");
        if (overwriteMerit == null) {
            throw new IllegalArgumentException("Delta cannot be null");
        } else if (Math.abs(overwriteMerit) > 1) {
            throw new IllegalArgumentException("OverwriteMerit must be in [-1, 1]");
        }

        var error = validateOverwriteMerit(overwriteMerit, getOriginalMerit());
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.overwriteMerit = overwriteMerit;
    }

    public void setImpact(Impact impact) {
        logger.debug("Set Impact");
        if (impact == null) {
            throw new IllegalArgumentException("Impact cannot be null");
        }
        this.impact = impact;
    }

    public void setRequirement(Requirement requirement) {
        logger.debug("Set Requirement");
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement cannot be null");
        }
        this.requirement = requirement;
    }

    public Float getOriginalMerit(){
        return impact.getMerit();
    }

    public Color getMeritColor() {
        var r = (int) Math.max(-this.overwriteMerit * 255, 0);
        var g = (int) Math.max(this.overwriteMerit * 255, 0);
        return new Color(r, g, 0);
    }

    @Override
    public Analysis getAnalysis() {
        return getRequirement().getAnalysis();
    }
}
