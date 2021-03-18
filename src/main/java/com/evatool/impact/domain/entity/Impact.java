package com.evatool.impact.domain.entity;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "IMP_IMPACT")
@Entity(name = "IMP_IMPACT")
public class Impact extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Impact.class);

    @Getter
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    private NumericImpactId numericId;

    @Column(name = "VALUE", nullable = false)
    @Getter
    private double value;

    @Column(name = "DESCRIPTION", nullable = false)
    @Getter
    private String description;

    @Getter
    @ManyToOne(optional = false)
    private Dimension dimension;

    @Getter
    @ManyToOne(optional = false)
    private ImpactStakeholder stakeholder;

    @Getter
    @ManyToOne(optional = false)
    private ImpactAnalysis analysis;

    public Impact() {
        super();
        this.setNumericId(new NumericImpactId());
        logger.debug("{} created", Impact.class.getSimpleName());
    }

    public Impact(double value, String description, Dimension dimension, ImpactStakeholder stakeholder, ImpactAnalysis analysis) {
        this();
        this.setValue(value);
        this.setDescription(description);
        this.setDimension(dimension);
        this.setStakeholder(stakeholder);
        this.setAnalysis(analysis);
    }

    @Override
    public String toString() {
        return "Impact{" +
                "id=" + this.id +
                ", numericId=" + this.numericId +
                ", value=" + this.value +
                ", description='" + this.description + '\'' +
                ", dimension=" + this.dimension +
                ", stakeholder=" + this.stakeholder +
                ", analysis=" + this.analysis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        var that = (Impact) o;
        return super.equals(that)
                && Objects.equals(this.numericId, that.numericId)
                && Double.compare(this.value, that.value) == 0
                && Objects.equals(this.description, that.description)
                && Objects.equals(this.dimension, that.dimension)
                && Objects.equals(this.stakeholder, that.stakeholder)
                && Objects.equals(this.analysis, that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.value, this.description, this.dimension, this.stakeholder, this.analysis);
    }

    public void setNumericId(NumericImpactId numericId) {
        logger.debug("Set NumericId");
        if (numericId == null) {
            logger.error("Attempted to set numericId to null");
            throw new IllegalArgumentException("NumericId cannot be null.");
        } else if (this.numericId != null && this.numericId.getId() != null) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("NumericId Cannot be changed.");
        }
        this.numericId = numericId;
    }

    public void setValue(double value) {
        logger.debug("Set Value");
        if (value < -1.0 || value > 1.0) {
            logger.error("Attempted to set value outside its valid range");
            throw new IllegalArgumentException("Value must be in range [-1, 1]");
        }
        this.value = value;
    }

    public void setDescription(String description) {
        logger.debug("Set Description");
        if (description == null) {
            logger.error("Attempted to set description to null");
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description;
    }

    public void setDimension(Dimension dimension) {
        logger.debug("Set Dimension");
        if (dimension == null) {
            logger.error("Attempted to set dimension description to null");
            throw new IllegalArgumentException("Dimension cannot be null.");
        }
        this.dimension = dimension;
    }

    public void setStakeholder(ImpactStakeholder stakeholder) {
        logger.debug("Set Stakeholder");
        if (stakeholder == null) {
            logger.error("Attempted to set stakeholder to null");
            throw new IllegalArgumentException("Stakeholder cannot be null.");
        }
        this.stakeholder = stakeholder;
    }

    public void setAnalysis(ImpactAnalysis analysis) {
        logger.debug("Set Analysis");
        if (analysis == null) {
            logger.error("Attempted to set analysis to null");
            throw new IllegalArgumentException("Analysis cannot be null.");
        }
        this.analysis = analysis;
    }
}
