package com.evatool.analysis.domain.model;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "ANA_ANALYSIS")
@EqualsAndHashCode
public class Analysis {

    @Id
    @Getter
    @Setter
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private Date lastUpdate;


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

    @Override
    public String toString() {
        return "Analysis{" +
                "analysisId=" + analysisId +
                ", analysisName='" + analysisName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", date='" + lastUpdate + '\'' +
                '}';
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
