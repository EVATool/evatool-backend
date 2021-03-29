package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode
@ToString
public class SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(SuperEntity.class);

    protected SuperEntity() {
        logger.debug("{} created", SuperEntity.class.getSimpleName());
    }

    @Getter
    @Id
    @GeneratedValue(generator = "SuperEntityUuidGenerator")
    @GenericGenerator(name = "SuperEntityUuidGenerator", strategy = "com.evatool.impact.domain.entity.SuperEntityUuidGenerator")
    @Type(type= "uuid-char")
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    protected UUID id;

    public void setId(UUID id) {
        logger.debug("Set id");
        if (this.id != null) {
            logger.error("Attempted to set existing id");
            throw new IllegalArgumentException("Existing id cannot be set.");
        }
        this.id = id;
    }
}
