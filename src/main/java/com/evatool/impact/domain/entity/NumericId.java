package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Table(name = "IMP_NUMERIC_ID")
@Entity(name = "IMP_NUMERIC_ID")
@EqualsAndHashCode // TODO Use this in all classes and test if its adequate
public class NumericId {

    private static final Logger logger = LoggerFactory.getLogger(NumericId.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Integer id;

    public void setId(Integer id) {
        if (idAlreadySet()) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("NumericId Cannot be changed.");
        }
        this.id = id;
    }

    private boolean idAlreadySet() {
        return this.id != null;
    }
}
