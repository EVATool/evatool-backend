package com.evatool.requirements.entity;

import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "REQ_Dimension")
public class RequirementDimension {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    public RequirementDimension(String name) {
        this.name = name;
    }

    public RequirementDimension() {

    }

    public static  RequirementDimension fromJson(String json){
        return  new Gson().fromJson(json, RequirementDimension.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



}
