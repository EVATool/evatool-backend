package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "IMP_ANALYSIS")
@Table(name = "IMP_ANALYSIS")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImpactAnalysis extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(ImpactAnalysis.class);

    public ImpactAnalysis() {
        super();
        logger.debug("{} created", ImpactAnalysis.class.getSimpleName());
    }

    public ImpactAnalysis(UUID id) {
        this();
        this.setId(id);
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
}
