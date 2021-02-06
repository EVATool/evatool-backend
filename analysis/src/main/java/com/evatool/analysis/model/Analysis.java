package com.evatool.analysis.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * @author fobaidi
 * @author MHallweg
 */
@Entity
@Table(name = "project_analysis")
public class Analysis {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",
            strategy = "uuid")
    @Getter
    private String analysisId;

    /**
     * Name of the Analysis {@link String}
     */
    @Getter
    @Setter
    private String analysisName;

    /**
     * The description of the Analysis {@link String}
     */
    @Getter
    @Setter
    private String description;

    /**
     * The User of the Analysis {@link java.util.List<User>}
     * Referenz zum User
     */
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> analysisUserId;

    /**
     * The Stakeholder of the Analysis {@link java.util.List<Stakeholder>}
     * Referenz zum Stakholder
     */
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stakeholder> analysisStakeholderId;

    public Analysis() {
    }
}