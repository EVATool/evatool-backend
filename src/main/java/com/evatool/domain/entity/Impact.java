package com.evatool.domain.entity;

import com.evatool.common.validation.OverwriteMeritValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "impact")
@Entity(name = "impact")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Impact extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Impact.class);

    @Column(name = "merit", nullable = false)
    @Getter
    private Float merit;

    @Column(name = "description", nullable = false, length = 2048)
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Value value;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Stakeholder stakeholder;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(cascade = CascadeType.DETACH, orphanRemoval = true, mappedBy = "impact")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<RequirementDelta> requirementDeltas = new HashSet<>();

    public Impact(Float merit, String description, Value value, Stakeholder stakeholder, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setMerit(merit);
        setDescription(description);
        setValue(value);
        setStakeholder(stakeholder);
        setAnalysis(analysis);
    }

    private Impact() {

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
        analysis.getImpacts().add(this);
        value.getImpacts().add(this);
        stakeholder.getImpacts().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        analysis.getImpacts().remove(this);
        value.getImpacts().remove(this);
        stakeholder.getImpacts().remove(this);
    }

    public void setMerit(Float merit) {
        logger.trace("Set Merit");
        if (Math.abs(merit) > 1) {
            throw new IllegalArgumentException("Merit must be in [-1, 1]");
        }

        this.merit = merit;

        // Ensure that deltas referencing this impact have a valid overwriteMerit.
        for (var delta : requirementDeltas) {
            var error = OverwriteMeritValidation.validateOverwriteMerit(delta.getOverwriteMerit(), getMerit());
            if (error != null) {
                delta.setOverwriteMerit(getMerit());
            }
        }
    }

    public Boolean getIsGoal() {
        return merit >= 0;
    }

    public Boolean getIsRisk() {
        return !getIsGoal();
    }

    @Override
    public String getPrefix() {
        return "IMP";
    }

    @Override
    public String getParentId() {
        return this.analysis.getId().toString();
    }

    @Override
    public String getParentClass() {
        return "Analysis";
    }

    @Override
    public String getChildClass() {
        return "Impact";
    }
}
