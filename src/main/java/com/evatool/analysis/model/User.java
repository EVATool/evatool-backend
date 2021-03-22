package com.evatool.analysis.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * This class represents a User
 *
 * @author fobaidi
 * @author MHallweg
 */
@Entity
@Table(name = "ANA_USER")
public class User {

    @Id
    @Getter
    @Setter
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID userId = UUID.randomUUID();

    /**
     * Name of the user {@link String}
     */
    @Getter
    private String userName;

    /**
     * The userPassword {@link String}
     */
    @Getter
    private String userPassword;

    /**
     * The Email {@link String}
     */
    @Getter
    private String userEmail;

    public User(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public User() {
    }

    public void setUserName(String userName) {
        if (userName == null){
            throw new IllegalArgumentException("User name cannot be null.");
        }
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        if (userPassword == null){
            throw new IllegalArgumentException("userPassword  cannot be null.");
        }
        this.userPassword = userPassword;
    }

    public void setUserEmail(String userEmail) {
        if (userEmail == null){
            throw new IllegalArgumentException("userEmail name cannot be null.");
        }
        this.userEmail = userEmail;
    }
}
