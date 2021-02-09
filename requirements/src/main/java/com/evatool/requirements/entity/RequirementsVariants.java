package com.evatool.requirements.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class RequirementsVariants {

    @Id
    private UUID id = UUID.randomUUID();
    private String titel;
    private String description;


    public RequirementsVariants() {
    }

    public RequirementsVariants(String titel, String description) {
        this.titel = titel;
        this.description = description;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.titel = titel;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
