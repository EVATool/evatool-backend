package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
    @Setter
    @ManyToOne(optional = false)
    private Impact impact;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Requirement requirement;

    public RequirementDelta(Impact impact, Requirement requirement, Float overwriteMerit) {
        this(impact, requirement);
        setOverwriteMerit(overwriteMerit);
    }

    public RequirementDelta(Impact impact, Requirement requirement) {
        super();
        logger.trace("Constructor");
        setImpact(impact);
        setOverwriteMerit(impact.getMerit());
        setRequirement(requirement);
    }

    private RequirementDelta() {

    }

    @PrePersist
    @PreUpdate
    @PreRemove
    void prePersistUpdateRemove() {
        logger.trace("Pre Persist/Pre Update/Pre Remove");
        FindByAnalysis.super.updateAnalysisLastUpdated();
    }

    @PostPersist
    void postPersist() {
        logger.trace("Post Persist");
        impact.getRequirementDeltas().add(this);
        requirement.getRequirementDeltas().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        impact.getRequirementDeltas().remove(this);
        requirement.getRequirementDeltas().remove(this);
    }

    public void setOverwriteMerit(Float overwriteMerit) {
        logger.trace("Set Overwrite Merit");
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

    public Float getOriginalMerit() {
        logger.trace("Get Overwrite Merit");
        return impact.getMerit();
    }

    public Color getMeritColor() {
        logger.trace("Get Merit Color");
        if (impact.getMerit() == 0) {
            return new Color(0.4f, 0.4f, 0.4f);
        } else if (this.overwriteMerit > 0) {
            return new Color(1f - this.overwriteMerit, 1f, 0f);
        } else {
            return new Color(1f, 1f + this.overwriteMerit, 0);
        }
    }

    public Float getMinOverwriteMerit() {
        return getOriginalMerit() > 0 ? 0 : getOriginalMerit();
    }

    public Float getMaxOverwriteMerit() {
        return getOriginalMerit() < 0 ? 0 : getOriginalMerit();
    }

    @Override
    public Analysis getAnalysis() {
        return getRequirement().getAnalysis();
    }
}
