package com.evatool.analysis.domain.model;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ANA_ANALYSIS")
@EqualsAndHashCode
@ToString
public class Analysis {

    @Id
    @Getter
    @Setter
    @Type(type = "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID analysisId = UUID.randomUUID();

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private NumericId numericId;

    public String getUniqueString() {
        if (this.numericId == null || this.numericId.getNumericId() == null)
            return null;
        else
            return "ANA" + this.numericId.getNumericId();
    }

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

    @Getter
    @Setter
    private Boolean isTemplate = false;

    @Getter
    @Setter
    @OneToMany
    private Set<Value> valueSet;

    public Analysis(String analysisName, String description) {
        this();
        this.analysisName = analysisName;
        this.description = description;
    }

    public Analysis() {
        this.numericId = new NumericId();
    }

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
