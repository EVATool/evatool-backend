package com.evatool.analysis.model;

import com.evatool.analysis.enums.Dimension;
import com.google.gson.Gson;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ANA_ANALYSIS_IMPACTS")
@Getter
public class AnalysisImpacts {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String title;
    private double value;
    private String description;
    private Dimension dimension;

    public AnalysisImpacts(){}

    public AnalysisImpacts(String title, String description, double value, Dimension dimension) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.dimension = dimension;
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

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < -1 || value > 1) {
            throw new IllegalArgumentException("Value must be in range [-1, 1]");
        }
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
