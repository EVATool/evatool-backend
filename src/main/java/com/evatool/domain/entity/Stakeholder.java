package com.evatool.domain.entity;

import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
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

@Entity(name = "stakeholder")
@Table(name = "stakeholder")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Stakeholder extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Stakeholder.class);

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private StakeholderPriority priority;

    @Getter
    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private StakeholderLevel level;

    @Getter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "stakeholder")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Impact> impacts = new HashSet<>();

    public Stakeholder(String name, StakeholderPriority priority, StakeholderLevel level, Analysis analysis) {
        super();
        logger.debug("Constructor");
        setName(name);
        setPriority(priority);
        setLevel(level);
        setAnalysis(analysis);
    }

    private Stakeholder() {

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
        analysis.getStakeholders().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        analysis.getStakeholders().remove(this);
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void setPriority(StakeholderPriority priority) {
        logger.debug("Set Priority");
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }

    public void setLevel(StakeholderLevel level) {
        logger.debug("Set Level");
        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }
        this.level = level;
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
        String prefix;

        switch (level) {
            case INDIVIDUAL:
                prefix = "IND";
                break;

            case ORGANIZATION:
                prefix = "ORG";
                break;

            case SOCIETY:
                prefix = "SOC";
                break;

            default:
                throw new IllegalArgumentException("Unhandled Stakeholder level.");
        }

        return prefix;
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
        return "Stakeholder";
    }
}
