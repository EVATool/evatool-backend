
package com.evatool.requirements.domain.entity;



import com.google.gson.Gson;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "REQ_RequirementImpact")
public class RequirementsImpact {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String description;
    private Double value;

    @ManyToOne
    private RequirementValue requirementValue;

    public RequirementsImpact() {
    }

   public static RequirementsImpact fromJson(String json){
        return  new Gson().fromJson(json, RequirementsImpact.class);

    }

    public RequirementsImpact(String description, Double value, RequirementValue requirementValue) {
        this.description = description;
        this.value = value;
        this.requirementValue = requirementValue;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        if (value < -1 || value > 1) {
            throw new IllegalArgumentException("Value must be in range [-1, 1]");
        }
        this.value = value;
    }

    public RequirementValue getRequirementDimension() {
        return requirementValue;
    }

    public void setRequirementDimension(RequirementValue requirementValue) {
        if (requirementValue == null) {
            throw new IllegalArgumentException("RequirementDimension cannot be null.");
        }
        this.requirementValue = requirementValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
