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

@Entity(name = "value_type")
@Table(name = "value_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValueType extends SuperEntity implements FindByAnalysis {
    private static final Logger logger = LoggerFactory.getLogger(ValueType.class);

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
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "valueType")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Value> values = new HashSet<>();

    public ValueType(String name, String description, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setName(name);
        setDescription(description);
        setAnalysis(analysis);
    }

    private ValueType() {

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
        analysis.getValueTypes().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        analysis.getValueTypes().remove(this);
    }
}
