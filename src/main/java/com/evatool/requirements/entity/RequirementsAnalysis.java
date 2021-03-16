package com.evatool.requirements.entity;

import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "REQ_Analysis")
public class RequirementsAnalysis {

    @Id
    private UUID analysisId;

    public UUID getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(UUID analysisId) {
        this.analysisId = analysisId;
    }

    public static  RequirementsAnalysis fromJson(String json){
        System.out.println(json);
        return  new Gson().fromJson(json, RequirementsAnalysis.class);
    }
}
