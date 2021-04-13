package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.model.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class AnalysisDTO {

    private UUID rootEntityID;
    private String analysisName;
    private String analysisDescription;
    private String image;
    private Date lastUpdate;
    private Boolean isTemplate;
    private String uniqueString;

    public void setRootEntityID(UUID rootEntityID) {
        if (this.getRootEntityID() == null){
            throw new IllegalArgumentException("RootEntityId cannot be null");
        }
        this.rootEntityID = rootEntityID;
    }

    public void setAnalysisName(String analysisName) {
        if (this.getAnalysisName() == null){
            throw new IllegalArgumentException("Analysis name cannot be null.");
        }
        this.analysisName = analysisName;
    }

    public void setAnalysisDescription(String analysisDescription) {
        if (this.getAnalysisDescription() == null){
            throw new IllegalArgumentException("Analysis description cannot be null.");
        }
        this.analysisDescription = analysisDescription;
    }

    public void setImage(String image) {
        if (this.getImage() == null){
            throw new IllegalArgumentException("Analysis image cannot be null.");
        }
        this.image = image;
    }

    public void setLastUpdate(Date lastUpdate) {
        if (this.getLastUpdate() == null){
            throw new IllegalArgumentException("Analysis last update cannot be null.");
        }
        this.lastUpdate = lastUpdate;
    }

    public void setTemplate(Boolean template) {
        if (this.getIsTemplate() == null){
            throw new IllegalArgumentException("Analysis template cannot be null.");
        }
        isTemplate = template;
    }

    public void setUniqueString(String uniqueString) {
        if (this.getUniqueString() == null){
            throw new IllegalArgumentException("Analysis unique string cannot be null.");
        }
        this.uniqueString = uniqueString;
    }
}
