package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class PrefixIdEntity extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(PrefixIdEntity.class);

    @Getter
    @Setter
    @Column(name = "sequence_id", nullable = false, updatable = false)
    private Integer sequenceId;

    public abstract String getPrefix();

    public abstract String getParentId();

    public abstract String getParentClass();

    public abstract String getChildClass();

    public String getPrefixSequenceId() {
        logger.trace("Get Prefix Sequence Id");
        if (this.sequenceId == null)
            return null;
        else
            return getPrefix() + this.sequenceId;
    }
}
