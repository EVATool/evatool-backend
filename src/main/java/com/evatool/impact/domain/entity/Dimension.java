package com.evatool.impact.domain.entity;

import com.evatool.impact.common.exception.PropertyViolationException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "IMP_DIMENSION")
@Table(name = "IMP_DIMENSION")
public class Dimension extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Dimension.class);

    public enum Type {
        SOCIAL,
        ECONOMIC
    }

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private Type type;

    @Getter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public Dimension() {
        super();
    }

    private Dimension(String name, String description) {
        this();
        this.setName(name);
        this.setDescription(description);
    }

    public Dimension(String name, String type, String description) {
        this(name, description);
        this.setType(type);
    }

    public Dimension(String name, Type type, String description) {
        this(name, description);
        this.setType(type);
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            logger.error("Attempted to set name to null.");
            throw new PropertyViolationException("Name cannot be null.");
        }
        this.name = name;
    }

    public void setType(Type type) {
        if (type == null) {
            logger.error("Attempted to set type to null");
            throw new PropertyViolationException("Type cannot be null.");
        }
        this.type = type;
    }

    public void setType(String type) {
        if (!isValidType(type)) {
            logger.error("Invalid Dimension Type");
            throw new PropertyViolationException(String.format(
                    "Dimension type must be in %s.", Arrays.asList(Type.values())));
        }
        this.setType(Type.valueOf(type));
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            logger.error("Attempted to set description to null.");
            throw new PropertyViolationException("Description cannot be null.");
        }
        this.description = description;
    }

    private boolean isValidType(String value) {
        if (value == null) {
            return false;
        }
        for (var e : Type.values()) {
            if (value.equals(e.toString())) {
                return true;
            }
        }
        return false;
    }
}