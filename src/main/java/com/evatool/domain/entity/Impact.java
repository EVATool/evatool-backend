package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    private String description;

    @Getter
    @ManyToOne(optional = false)
    private Value value;

    @Getter
    @ManyToOne(optional = false)
    private Stakeholder stakeholder;

    @Getter
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
        logger.debug("Constructor");
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
        logger.debug("Pre Persist/Pre Update/Pre Remove");
        FindByAnalysis.super.updateAnalysisLastUpdated();
    }

    @PostPersist
    void postPersist() {
        logger.debug("Post Persist");
        analysis.getImpacts().add(this);
        value.getImpacts().add(this);
        stakeholder.getImpacts().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        analysis.getImpacts().remove(this);
        value.getImpacts().remove(this);
        stakeholder.getImpacts().remove(this);
    }

    public void setMerit(Float merit) {
        if (merit == null) {
            throw new IllegalArgumentException("Merit cannot be null");
        } else if (Math.abs(merit) > 1) {
            throw new IllegalArgumentException("Merit must be in [-1, 1]");
        }
        this.merit = merit;
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public void setValue(Value value) {
        logger.debug("Set Value");
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        this.value = value;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        logger.debug("Set Stakeholder");
        if (stakeholder == null) {
            throw new IllegalArgumentException("Stakeholder cannot be null");
        }
        this.stakeholder = stakeholder;
    }

    private void setAnalysis(Analysis analysis) {
        logger.debug("Set Analysis");
        if (analysis == null) {
            throw new IllegalArgumentException("Analysis cannot be null");
        }
        this.analysis = analysis;
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
