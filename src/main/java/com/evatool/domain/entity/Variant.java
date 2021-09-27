package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity(name = "variant")
@Table(name = "variant")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Variant extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Variant.class);

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
    private VariantType variantType;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Analysis analysis;

    public Variant(String name, String description, Boolean archived, VariantType variantType, Analysis analysis) {
        super();
        logger.trace("Constructor");
        setName(name);
        setDescription(description);
        setArchived(archived);
        setVariantType(variantType);
        setAnalysis(analysis);
    }

    private Variant() {

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
        analysis.getVariants().add(this);
        variantType.getVariants().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.trace("Post Remove");
        analysis.getVariants().remove(this);
        variantType.getVariants().remove(this);
    }

    @Override
    public String getPrefix() {
        return "VAR";
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
        return "Variant";
    }
}
