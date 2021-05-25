package com.evatool.domain.entity;

import com.evatool.common.enums.ValueType;
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

@Entity(name = "value")
@Table(name = "value")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Value extends SuperEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Value.class);

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Column(name = "type", nullable = false)
    private ValueType type;

    @Getter
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived;

    @Getter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "value")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Impact> impacts = new HashSet<>();

    public Value(String name, String description, ValueType type, Boolean archived, Analysis analysis) {
        super();
        logger.debug("Constructor");
        setName(name);
        setDescription(description);
        setType(type);
        setIsArchived(archived);
        setAnalysis(analysis);
    }

    private Value(){

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
        analysis.getValues().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        analysis.getValues().remove(this);
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public void setType(ValueType type) {
        logger.debug("Set Value Type");
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.type = type;
    }

    public void setIsArchived(Boolean isArchived) {
        logger.debug("Set IsArchived");
        if (isArchived == null) {
            throw new IllegalArgumentException("Archived cannot be null");
        }
        this.isArchived = isArchived;
    }

    private void setAnalysis(Analysis analysis) {
        logger.debug("Set Analysis");
        if (analysis == null) {
            throw new IllegalArgumentException("Analysis cannot be null");
        }
        this.analysis = analysis;
    }
}
