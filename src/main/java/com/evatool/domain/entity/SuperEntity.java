package com.evatool.domain.entity;

import com.evatool.application.service.TenancySentinel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.UUID;

import static com.evatool.common.validation.UsernameRealmValidation.validateUsernameOrRealm;

@MappedSuperclass
@EqualsAndHashCode
@ToString
public abstract class SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(SuperEntity.class);

    @Getter
    @Id
    @GeneratedValue(generator = "PrePersistGenerator")
    @GenericGenerator(name = "PrePersistGenerator", strategy = "com.evatool.domain.entity.PrePersistGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    protected UUID id;

    @Getter
    @Column(name = "realm", updatable = false, nullable = false)
    @EqualsAndHashCode.Exclude
    private String realm;

    @PrePersist
    private void prePersist() {
        logger.trace("Pre Persist");
        TenancySentinel.handleCreate(this);
        if (this.realm == null) {
            logger.debug("Realm was null, using default fallback realm \"evatool-realm\"");
            this.setRealm("evatool-realm");
        }
    }

    @PreUpdate
    @PreRemove
    private void preUpdateRemove() {
        logger.trace("Pre Update/Remove");
        TenancySentinel.handleUpdateOrDelete(this);
    }

    public void setId(UUID id) {
        logger.trace("Set Id");
        if (this.id != null) {
            throw new IllegalArgumentException("Existing id cannot be set");
        }
        this.id = id;
    }

    public void setRealm(String realm) {
        logger.trace("Set Realm");
        if (this.realm != null) {
            throw new IllegalArgumentException("Existing realm cannot be set");
        }

        var error = validateUsernameOrRealm(realm);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.realm = realm;
    }
}
