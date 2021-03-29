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
@ToString
public class ImpactStakeholder extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(ImpactStakeholder.class);

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    public ImpactStakeholder() {
        super();
        logger.debug("{} created", ImpactStakeholder.class.getSimpleName());
    }

    public ImpactStakeholder(UUID id, String name) {
        this();
        this.setId(id);
        this.setName(name);
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
}
