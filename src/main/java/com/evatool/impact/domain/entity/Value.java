package com.evatool.impact.domain.entity;

import com.evatool.impact.common.ImpactValueType;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "IMP_VALUE")
@Table(name = "IMP_VALUE")
public class Value extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Value.class);

    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter
    @Column(name = "TYPE", nullable = false)
    private ImpactValueType type;

    @Getter
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public Value() {
        super();
        logger.debug("{} created", Value.class.getSimpleName());
    }

    public Value(String name, ImpactValueType type, String description) {
        this();
        this.setName(name);
        this.setType(type);
        this.setDescription(description);
    }

    @Override
    public String toString() {
        return "value{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        var that = (Value) o;
        return super.equals(that)
                && Objects.equals(this.name, that.name)
                && this.type == that.type
                && Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.name, this.type, this.description);
    }

    public void setName(String name) {
        logger.debug("Set Name");
        if (name == null) {
            logger.error("Attempted to set name to null");
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    public void setType(ImpactValueType type) {
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
