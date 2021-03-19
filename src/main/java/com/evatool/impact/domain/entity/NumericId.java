package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Table(name = "IMP_NUMERIC_ID")
@Entity(name = "IMP_NUMERIC_ID")
@EqualsAndHashCode
public class NumericId {

    private static final Logger logger = LoggerFactory.getLogger(NumericId.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Integer id;
}
