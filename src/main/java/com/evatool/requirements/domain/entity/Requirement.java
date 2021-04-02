
package com.evatool.requirements.domain.entity;

import com.google.gson.Gson;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "REQ_Requirement")
public class Requirement {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private final UUID id = UUID.randomUUID();
    private String description;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<RequirementsVariant> variants = new ArrayList<>();
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<RequirementPoint> requirementPointCollection = new ArrayList<>();

    @ManyToOne
    private RequirementsAnalysis requirementsAnalysis;

    private Integer numericId;

    public Requirement() {
    }

    public Requirement(String description, RequirementsAnalysis requirementsAnalysis,Collection<RequirementsVariant> variants) {
        this.description = description;
        this.requirementsAnalysis = requirementsAnalysis;
        this.variants = variants;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Collection<RequirementsVariant> getVariants() {
        return variants;
    }

    public void setVariants(Collection<RequirementsVariant> variants) throws IllegalArgumentException {
        if (variants == null) {
            throw new IllegalArgumentException("Variants cannot be null.");
        }
        this.variants = variants;
    }

    public UUID getId() {
        return id;
    }

    public RequirementsAnalysis getRequirementsAnalysis() {
        return requirementsAnalysis;
    }

    public void setRequirementsAnalysis(RequirementsAnalysis requirementsAnalysis) {
        if (requirementsAnalysis == null) {
            throw new IllegalArgumentException("RequirementsAnalysis cannot be null.");
        }
        this.requirementsAnalysis = requirementsAnalysis;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", variants=" + variants +
                ", requirementPointCollection=" + requirementPointCollection +
                ", requirementsAnalysis=" + requirementsAnalysis +
                ", numericId=" + numericId +
                '}';
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Collection<RequirementPoint> getRequirementPointCollection() {
        return requirementPointCollection;
    }

    public void setRequirementPointCollection(Collection<RequirementPoint> requirementPointCollection) {
        this.requirementPointCollection = requirementPointCollection;
    }

    public Integer getNumericId() {
        return numericId;
    }

    public void setNumericId(Integer numericId) {
        this.numericId = numericId;
    }
}

