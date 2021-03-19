package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.persistence.*;

@Table(name = "IMP_NUMERIC_ID")
@Entity(name = "IMP_NUMERIC_ID")
@EqualsAndHashCode // TODO [philipp] Use this in all classes and test if its adequate
@ToString
public class NumericId {

    private static final Logger logger = LoggerFactory.getLogger(NumericId.class);

    private static final String PREFIX = "IMP";

    @Id
    @GeneratedValue(generator = "UniqueImpactAnalysisIdGenerator")
    @GenericGenerator(name = "UniqueImpactAnalysisIdGenerator", strategy = "com.evatool.impact.domain.entity.UniqueImpactAnalysisIdGenerator")
    @Getter
    private Integer numericId;

    public void setNumericId(Integer numericId) {
        if (this.numericIdAlreadySet()) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("NumericId Cannot be changed.");
        }
        this.numericId = numericId;
    }

    private boolean numericIdAlreadySet() {
        return this.numericId != null;
    }

    public String _getReadableId() { // Not prefixing this method with '_' causes some tests to fail.
        if (numericIdAlreadySet()) {
            return PREFIX + this.numericId;
        } else {
            return null;
        }
    }

    @PrePersist
    void prePersist() {
        if (this.numericId != null) {
            throw new InvalidDataAccessApiUsageException(this.getClass().getSimpleName());
        }
    }
}
