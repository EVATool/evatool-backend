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
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Getter
    @Setter
    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @Getter
    @Setter
    @Column(name = "image_url")
    private String imageUrl;

    @Getter
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
    void prePersistUpdate() {
        logger.debug("Pre Post/Pre Update");
        wasUpdated();
    }

    public void wasUpdated() {
        logger.debug("Was Updated");
        lastUpdated = new Date().getTime();
    }

    public Date getLastUpdatedDate() {
        return Date.from(Instant.ofEpochMilli(lastUpdated));
    }

    public String getLastUpdatedPreformatted() {
        return getLastUpdatedDate().toString();
    }

    @Override
    public String getPrefix() {
        if (Boolean.TRUE.equals(isTemplate)) {
            return "TMP";
        } else {
            return "ANA";
        }
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
