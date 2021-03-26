package com.evatool.analysis.domain.model;

import com.evatool.analysis.domain.enums.Dimension;
import com.google.gson.Gson;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ANA_ANALYSIS_IMPACTS")
@Getter
public class AnalysisImpacts {

    @Id
    private UUID id = UUID.randomUUID();
    private String title;
    private double impactValue;
    private String description;
    @ManyToOne(optional = false)
    private Value value;

    public AnalysisImpacts(){}

    public AnalysisImpacts(String title, String description, double impactValue, Value value) {
        this.title = title;
        this.description = description;
        this.impactValue = impactValue;
        this.value = value;
    }

    public static AnalysisImpacts fromJson(String json){
        return new Gson().fromJson(json, AnalysisImpacts.class);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description;
    }

    public double getImpactValue() {
        return impactValue;
    }

    public void setImpactValue(int value) {
        if (value < -1 || value > 1) {
            throw new IllegalArgumentException("Value must be in range [-1, 1]");
        }
        this.impactValue = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
