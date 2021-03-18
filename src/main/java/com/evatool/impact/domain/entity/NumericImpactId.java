package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Table(name = "IMP_NUMERIC_IMPACT_ID")
@Entity(name = "IMP_NUMERIC_IMPACT_ID")
@EqualsAndHashCode
public class NumericImpactId {

    private static final Logger logger = LoggerFactory.getLogger(NumericImpactId.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Integer numericId;

    @OneToOne(mappedBy = "numericId", optional = false)
    private Impact impact;
}
