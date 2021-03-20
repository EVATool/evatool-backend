
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
    private int value;

    @ManyToOne
    private RequirementDimension requirementDimension;

    public RequirementsImpact() {
    }

   public static RequirementsImpact fromJson(String json){
        return  new Gson().fromJson(json, RequirementsImpact.class);

    }

    public RequirementsImpact(String description, int value, RequirementDimension requirementDimension) {
        this.description = description;
        this.value = value;
        this.requirementDimension = requirementDimension;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < -1 || value > 1) {
            throw new IllegalArgumentException("Value must be in range [-1, 1]");
        }
        this.value = value;
    }

    public RequirementDimension getRequirementDimension() {
        return requirementDimension;
    }

    public void setRequirementDimension(RequirementDimension requirementDimension) {
        if (requirementDimension == null) {
            throw new IllegalArgumentException("RequirementDimension cannot be null.");
        }
        this.requirementDimension = requirementDimension;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
