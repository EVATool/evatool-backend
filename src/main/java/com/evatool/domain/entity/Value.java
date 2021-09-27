package com.evatool.domain.entity;

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

@Entity(name = "value")
@Table(name = "value")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Value extends SuperEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Value.class);

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Setter
    @Column(name = "archived", nullable = false)
    private Boolean archived;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private ValueType valueType;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "value")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Impact> impacts = new HashSet<>();

    public Value(String name, String description, Boolean archived, ValueType valueType, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setName(name);
        setDescription(description);
        setArchived(archived);
        setValueType(valueType);
        setAnalysis(analysis);
    }

    private Value(){

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
        analysis.getValues().add(this);
        valueType.getValues().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        analysis.getValues().remove(this);
        valueType.getValues().remove(this);
    }
}
