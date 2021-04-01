package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "IMP_VALUE")
@Table(name = "IMP_VALUE")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImpactValue extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(ImpactValue.class);

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter
    @Column(name = "TYPE", nullable = false)
    private String type;

    @Getter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Getter
    @ManyToOne(optional = true) // TODO change to false when event from Analysis has analysisId
    private ImpactAnalysis analysis;

    public ImpactValue() {
        super();
        logger.debug("{} created", ImpactValue.class.getSimpleName());
    }

    public ImpactValue(UUID id, String name, String type, String description, ImpactAnalysis analysis) {
        this();
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setDescription(description);
        this.setAnalysis(analysis);
    }

    @Override
    public void setId(UUID id) {
        logger.debug("Set id");
        if (id == null) {
            logger.error("Attempted to set id to null");
            throw new IllegalArgumentException("Id cannot be null.");
        }
        super.setId(id);
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            logger.error("Attempted to set name to null");
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    public void setType(String type) {
        if (type == null) {
            logger.error("Attempted to set type to null");
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            logger.error("Attempted to set description to null");
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description;
    }

    // TODO Tests
    public void setAnalysis(ImpactAnalysis analysis) {
        logger.debug("Set Analysis");
        if (this.analysis != null) {
            logger.error("Attempted to set existing analysis");
            throw new IllegalArgumentException("Existing analysis cannot be set.");
        }
        this.analysis = analysis;
    }
}
