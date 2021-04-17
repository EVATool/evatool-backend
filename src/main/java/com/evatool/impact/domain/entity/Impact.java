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

@Table(name = "IMP_IMPACT")
@Entity(name = "IMP_IMPACT")
@EqualsAndHashCode(callSuper = true)
@ToString
public class Impact extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(Impact.class);

    @Getter
    private Integer numericId;

    @Column(name = "VALUE", nullable = false)
    @Getter
    private Double value;

    @Column(name = "DESCRIPTION", nullable = false, length = 4096)
    @Getter
    private String description;

    @Getter
    @ManyToOne(optional = false)
    private ImpactValue valueEntity;

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

    public Impact(double value, String description, ImpactValue valueEntity, ImpactStakeholder stakeholder, ImpactAnalysis analysis) {
        this();
        this.setValue(value);
        this.setDescription(description);
        this.setValueEntity(valueEntity);
        this.setStakeholder(stakeholder);
        this.setAnalysis(analysis);
    }

    public void setNumericId(Integer numericId) {
        logger.debug("Set NumericId");
        if (this.numericId != null) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("Existing numericId cannot be set.");
        }
        this.numericId = numericId;
    }

    public String getUniqueString() {
        if (this.numericId != null) {
            return "IMP" + this.getNumericId();
        } else {
            return null;
        }
    }

    public void setValue(Double value) {
        logger.debug("Set ImpactValue");
        if (value == null) {
            logger.error("Attempted to set value to null");
            throw new IllegalArgumentException("ImpactValue cannot be null");
        } else if (value < -1.0 || value > 1.0) {
            logger.error("Attempted to set value outside its valid range");
            throw new IllegalArgumentException("ImpactValue must be in range [-1, 1]");
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

    public void setValueEntity(ImpactValue impactValue) {
        logger.debug("Set ImpactValue (ImpactValue)");
        if (impactValue == null) {
            logger.error("Attempted to set ImpactValue description to null");
            throw new IllegalArgumentException("ImpactValue cannot be null.");
        }
        this.valueEntity = impactValue;
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
