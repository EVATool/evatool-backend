package com.evatool.requirements.entity;

import com.google.gson.Gson;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "REQ_Analysis")
public class RequirementsAnalysis {

    @Id
    @Type(type = "uuid-char")

    private UUID analysisId;

    public RequirementsAnalysis() {
        analysisId = UUID.randomUUID();
    }

    public UUID getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(UUID analysisId) {
        this.analysisId = analysisId;
    }

    public static RequirementsAnalysis fromJson(String json) {
        return new Gson().fromJson(json, RequirementsAnalysis.class);
    }
}
