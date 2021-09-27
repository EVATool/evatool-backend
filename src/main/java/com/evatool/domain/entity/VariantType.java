package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity(name = "variant_type")
@Table(name = "variant_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VariantType extends SuperEntity implements FindByAnalysis {
    private static final Logger logger = LoggerFactory.getLogger(VariantType.class);

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

    public VariantType(String name, String description, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setName(name);
        setDescription(description);
        setAnalysis(analysis);
    }

    private VariantType() {

    }

    @PrePersist
    @PreUpdate
    @PreRemove
    void prePersistUpdateRemove() {
        logger.trace("Pre Persist/Pre Update/Pre Remove");
        FindByAnalysis.super.updateAnalysisLastUpdated();
    }
}
