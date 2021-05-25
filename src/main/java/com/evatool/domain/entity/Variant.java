package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "variant")
@Table(name = "variant")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Variant extends PrefixIdEntity implements FindByAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(Variant.class);

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived;

    @Getter
    @ManyToOne(optional = false)
    private Analysis analysis;

    @Getter
    @OneToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude // Equality on List objects never returns true.
    private Set<Variant> subVariants;

    public Variant(String name, String description, Boolean archived, Analysis analysis) {
        super();
        logger.debug("Constructor");
        setName(name);
        setDescription(description);
        setIsArchived(archived);
        setAnalysis(analysis);
    }

    private Variant() {

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
        analysis.getVariants().add(this);
    }

    @PostRemove
    void postRemove() {
        logger.debug("Post Remove");
        analysis.getVariants().remove(this);
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
