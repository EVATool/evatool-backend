package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "IMP_REQUIREMENT")
@Table(name = "IMP_REQUIREMENT")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImpactRequirement extends SuperEntity {
    private static final Logger logger = LoggerFactory.getLogger(ImpactRequirement.class);

    @Getter
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Getter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public ImpactRequirement() {
        super();
        logger.debug("{} created", ImpactValue.class.getSimpleName());
    }

    public ImpactRequirement(String title, String description) {
        this();
        this.setTitle(title);
        this.setDescription(description);
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

    public void setTitle(String title) {
        logger.debug("set title");
        if (title == null) {
            logger.error("Attempted to set title to null");
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        logger.debug("set description");
        if (description == null) {
            logger.error("Attempted to set description to null");
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description;
    }
}
