package com.evatool.impact.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(SuperEntity.class);

    protected SuperEntity() {
        logger.debug("{} created", SuperEntity.class.getSimpleName());
    }

    @Getter
    @Id
    @ColumnTransformer(read = "BIN_TO_UUID(?)", write = "UUID_TO_BIN(?)")
    @GeneratedValue(generator = "SuperEntityUuidGenerator")
    @GenericGenerator(name = "SuperEntityUuidGenerator", strategy = "com.evatool.impact.domain.entity.SuperEntityUuidGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    protected UUID id;

    public void setId(UUID id) {
        logger.debug("Set id");
        if (this.idAlreadySet()) {
            logger.error("Attempted to set existing id");
            throw new IllegalArgumentException("Existing id cannot be set.");
        }
        this.id = id;
    }

    private boolean idAlreadySet() {
        return this.id != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        var that = (SuperEntity) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
