package com.evatool.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
public class User extends SuperEntity { // TODO A Remove completely!

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Getter
    @Setter
    @Column(name = "external_user_id", nullable = false)
    private String externalUserId;

    public User(String externalUserId) {
        super();
        logger.debug("Constructor");
        setExternalUserId(externalUserId);
    }

    private User() {

    }
}
