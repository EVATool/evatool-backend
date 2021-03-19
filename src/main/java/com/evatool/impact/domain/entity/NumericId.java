package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "IMP_NUMERIC_ID")
@Entity(name = "IMP_NUMERIC_ID")
@EqualsAndHashCode // TODO Use this in all classes and test if its adequate
@ToString
public class NumericId {

    private static final Logger logger = LoggerFactory.getLogger(NumericId.class);

    private static final String PREFIX = "IMP";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Integer id;

    private String readableId;

    public void setId(Integer id) {
        if (idAlreadySet()) {
            logger.error("Attempted to set existing numericId");
            throw new IllegalArgumentException("NumericId Cannot be changed.");
        }
        this.id = id;
        if (this.id != null) {
            this.readableId = PREFIX + this.id;
        }
    }

    private boolean idAlreadySet() {
        return this.id != null;
    }

    public String _getReadableId() { // Not prefixing this method with '_' causes some tests to fail.
        return this.readableId;
    }
}
