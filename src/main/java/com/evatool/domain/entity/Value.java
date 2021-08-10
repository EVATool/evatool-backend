package com.evatool.domain.entity;

import com.evatool.common.enums.ValueType;
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
    @Column(name = "type", nullable = false)
    private ValueType type;

    @Getter
    @Setter
    @Column(name = "archived", nullable = false)
    private Boolean archived;

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

    public Value(String name, String description, ValueType type, Boolean archived, Analysis analysis) {
        super();
        logger.debug("Constructor");
        setName(name);
        setDescription(description);
        setType(type);
        setArchived(archived);
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
}
