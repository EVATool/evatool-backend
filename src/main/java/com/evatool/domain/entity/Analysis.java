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
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "analysis")
@Table(name = "analysis")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Analysis extends PrefixIdEntity {

    private static final Logger logger = LoggerFactory.getLogger(Analysis.class);

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @Getter
    @Setter
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "last_updated", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long lastUpdated;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "analysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Impact> impacts = new HashSet<>();

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "analysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Requirement> requirements = new HashSet<>();

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "analysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Stakeholder> stakeholders = new HashSet<>();

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "analysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Value> values = new HashSet<>();

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "analysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Variant> variants = new HashSet<>();

    public Analysis(String name, String description, Boolean isTemplate, String imageUrl) {
        super();
        logger.debug("Constructor");
        setName(name);
        setDescription(description);
        setImageUrl(imageUrl);
        setIsTemplate(isTemplate);
        wasUpdated();
    }

    public Analysis(String name, String description, Boolean isTemplate) {
        this(name, description, isTemplate, null);
    }

    private Analysis() {

    }

    @PrePersist
    @PreUpdate
    void update() {
        logger.debug("Pre Post/Pre Update");
        wasUpdated();
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

    private void setIsTemplate(Boolean isTemplate) {
        logger.debug("Set IsTemplate");
        if (isTemplate == null) {
            throw new IllegalArgumentException("Is template cannot be null");
        }
        this.isTemplate = isTemplate;
    }

    public void wasUpdated() {
        logger.debug("Was Updated");
        lastUpdated = new Date().getTime();
    }

    public Date getLastUpdated() {
        return Date.from(Instant.ofEpochMilli(lastUpdated));
    }

    @Override
    public String getPrefix() {
        return "ANA";
    }

    @Override
    public String getParentId() {
        return "None"; // Analyses use a dummy parentId so it can be used with the generic code in PrePersistGenerator.
    }

    @Override
    public String getParentClass() {
        return "None";
    }

    @Override
    public String getChildClass() {
        if (Boolean.TRUE.equals(isTemplate)) {
            return "Analysis_Template";
        } else {
            return "Analysis";
        }
    }
}
