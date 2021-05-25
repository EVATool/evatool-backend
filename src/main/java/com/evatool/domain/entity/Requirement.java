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

@Entity(name = "requirement")
@Table(name = "requirement")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Requirement extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Requirement.class);

    @Getter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "requirement")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<RequirementDelta> requirementDeltas = new HashSet<>();

    @Getter
    @OneToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Variant> variants = new HashSet<>();

    public Requirement(String description, Analysis analysis) {
        super();
        logger.debug("Constructor");
        setDescription(description);
        setAnalysis(analysis);
    }

    private Requirement() {

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
        analysis.getRequirements().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        analysis.getRequirements().remove(this);
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
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
        return "REQ";
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
        return "Requirement";
    }
}
