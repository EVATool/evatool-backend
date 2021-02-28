
package com.evatool.requirements.entity;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "REQ_Requirement")
public class Requirement {

    @Id
    private final UUID id = UUID.randomUUID();
    private String title;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RequirementsVariant> variants = new ArrayList<>();

    @ManyToOne
    private RequirementsAnalysis requirementsAnalysis;

    public Requirement() {
    }

    public Requirement(String title, String description, RequirementsAnalysis requirementsAnalysis,Collection<RequirementsVariant> variants) {
        this.title = title;
        this.description = description;
        this.requirementsAnalysis = requirementsAnalysis;
        this.variants = variants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }

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
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", variants=" + variants +
                ", requirementsAnalysis=" + requirementsAnalysis +
                '}';
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this.toString());
    }
}

