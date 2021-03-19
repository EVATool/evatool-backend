package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.persistence.*;

@Table(name = "IMP_NUMERIC_ID")
@Entity(name = "IMP_NUMERIC_ID")
@IdClass(AnalysisImpactId.class)
@EqualsAndHashCode // TODO [philipp] Use this in all classes and test if its adequate
@ToString
public class NumericId {

    private static final Logger logger = LoggerFactory.getLogger(NumericId.class);

    private static final String PREFIX = "IMP";

    @Id
    @Getter
    private Integer numericId;

    @Id
    private String analysisId = "lol";

    @Column(name = "READABLE_ID")
    private String readableId;

    public NumericId() {

    }

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

    private boolean readableIdAlreadySet() {
        return this.readableId != null;
    }

    public String _getReadableId() { // Not prefixing this method with '_' causes some tests to fail.
        return this.readableId;
    }

    @PrePersist
    void prePersist() {
        if (this.numericId != null) {
            throw new InvalidDataAccessApiUsageException(this.getClass().getSimpleName());
        }
        if (!this.readableIdAlreadySet()) {
            this.readableId = PREFIX + this.numericId;
        }
    }
}
