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

@Entity(name = "IMP_STAKEHOLDER")
@Table(name = "IMP_STAKEHOLDER")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImpactStakeholder extends SuperEntity { // TODO ForeignEntity base class

    private static final Logger logger = LoggerFactory.getLogger(ImpactStakeholder.class);

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter
    @Column(name = "LEVEL", nullable = false)
    private String level;

    public ImpactStakeholder() {
        super();
        logger.debug("{} created", ImpactStakeholder.class.getSimpleName());
    }

    public ImpactStakeholder(UUID id, String name, String level) {
        this();
        this.setId(id);
        this.setName(name);
        this.setLevel(level);
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

    public void setLevel(String level) { // TODO tests
        logger.debug("Set Level");
        if (level == null) {
            logger.error("Attempted to set level to null");
            throw new IllegalArgumentException("Level cannot be null.");
        }
        this.level = level;
    }
}
