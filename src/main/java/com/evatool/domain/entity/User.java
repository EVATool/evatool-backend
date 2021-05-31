package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "user")
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends SuperEntity {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Getter
    @Column(name = "external_user_id", nullable = false)
    private String externalUserId;

    public User(String externalUserId) {
        super();
        logger.debug("Constructor");
        setExternalUserId(externalUserId);
    }

    private User() {

    }

    private void setExternalUserId(String externalUserId) {
        logger.debug("Set External User Id");
        if (externalUserId == null) {
            throw new IllegalArgumentException("External user ID cannot be null");
        }
        this.externalUserId = externalUserId;
    }
}
