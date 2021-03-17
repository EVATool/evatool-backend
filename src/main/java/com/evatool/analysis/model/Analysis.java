package com.evatool.analysis.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @author fobaidi
 * @author MHallweg
 */
@Entity
@Table(name = "ANA_ANALYSIS")
public class Analysis {

    @Id
    @Getter
    @Setter
    @Type(type = "uuid-char")
    @Column(columnDefinition = "CHAR(255)")
    private UUID analysisId = UUID.randomUUID();

    /**
     * Name of the Analysis {@link String}
     */
    @Getter
    private String analysisName;

    /**
     * The description of the Analysis {@link String}
     */
    @Getter
    private String description;

    /**
     * The User of the Analysis {@link java.util.List<User>}
     * Referenz zum User
     */
    @Getter
    @Setter
    @OneToMany
    private Set<User> analysisUserId;

    /**
     * The Stakeholder of the Analysis {@link java.util.List<Stakeholder>}
     * Referenz zum Stakholder
     */
    @Getter
    @Setter
    @OneToMany
    private Set<Stakeholder> analysisStakeholderId;

    public Analysis(String analysisName, String description) {
        this.analysisName = analysisName;
        this.description = description;
    }

    public Analysis() {}

    public void setAnalysisName(String analysisName) {
        if (analysisName == null) {
            throw new IllegalArgumentException("analysis name cannot be null.");
        }
        this.analysisName = analysisName;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null.");
        }
        this.description = description;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
