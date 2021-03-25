package com.evatool.impact.domain.entity;

import com.evatool.impact.common.DimensionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "IMP_DIMENSION")
@Table(name = "IMP_DIMENSION")
@EqualsAndHashCode(callSuper = true)
@ToString
public class Dimension extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Dimension.class);

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter
    @Column(name = "TYPE", nullable = false)
    private DimensionType type;

    @Getter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public Dimension() {
        super();
        logger.debug("{} created", Dimension.class.getSimpleName());
    }

    public Dimension(String name, DimensionType type, String description) {
        this();
        this.setName(name);
        this.setType(type);
        this.setDescription(description);
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            logger.error("Attempted to set name to null");
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    public void setType(DimensionType type) {
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
}
