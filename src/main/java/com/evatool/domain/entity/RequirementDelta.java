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

    public Float getOriginalMerit(){
        return impact.getMerit();
    }

    public Color getMeritColor() {
        if (impact.getMerit() == 0) {
            return new Color(0f, 0f, 0f);
        } else if (Boolean.TRUE.equals(impact.getIsGoal())) {
            return new Color(1 - this.overwriteMerit, 1, 1 - this.overwriteMerit);
        } else if (!Boolean.TRUE.equals(impact.getIsGoal())) {
            return new Color(1, 1 + this.overwriteMerit, 1 + this.overwriteMerit);
        } else {
            throw new IllegalStateException("The current combination of merit (" + impact.getMerit() + ") " +
                    "and overwriteMerit (" + overwriteMerit + ") does not have a color.");
        }
    }

    @Override
    public Analysis getAnalysis() {
        return getRequirement().getAnalysis();
    }
}
