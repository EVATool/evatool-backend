package com.evatool.requirements.domain.entity;

import com.google.gson.Gson;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "REQ_VALUE")
public class RequirementValue {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();

    private String name;

    public RequirementValue(String name) {
        this.name = name;
    }

    public RequirementValue() {

    }

    public static RequirementValue fromJson(String json){
        return  new Gson().fromJson(json, RequirementValue.class);
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
