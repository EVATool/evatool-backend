package com.evatool.impact.domain.entity;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "IMP_IMPACT")
@Entity(name = "IMP_IMPACT")
public class Impact extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Impact.class);

    @Getter
    private Integer numericId;

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

    // TODO [philipp] Write tests in service and rest controller that validate this behavior.
    //  - Correct Exceptions are thrown
    //  - Correct Http Return Codes
    public void setNumericId(Integer numericId) {
        logger.debug("Set NumericId");
        if (this.numericId != null) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("Existing numericId cannot be set.");
        }
        this.numericId = numericId;
    }

    public String getUniqueString() { // This method has to be prefixed with '_' to not confuse ModelMapper.
        if (this.numericId != null) {
            return "IMP" + this.getNumericId();
        } else {
            return null;
        }
    }

    public void setValue(Double value) {
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

        if (this.analysis != null) {
            logger.error("Attempted to set existing analysis");
            throw new IllegalArgumentException("Existing analysis cannot be set.");
        }

        this.analysis = analysis;
    }
}
