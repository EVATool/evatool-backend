package com.FAE.EVATool.Analysis.Model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This class represents a User
 * @author fobaidi
 * @author MHallweg
 */
@Entity
@Table (name = "Project_User")
public class User  {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    @Getter
    private String userId;

    /**
     * Name of the user {@linkString}
     */
    @Getter
    @Setter
    private String userName;

    /**
     * The userPassword {@linkString}
     */
    @Getter
    @Setter
    private String userPassword;

    /**
     * The Email {@linkString}
     */
    @Getter
    @Setter
    private String userEmail;



}