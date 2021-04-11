package com.evatool.requirements.domain.entity;

import com.google.gson.Gson;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "REQ_RequirementVariant")
@Table(name = "REQ_RequirementVariant")
public class RequirementsVariant {

    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private Boolean archived;

    public RequirementsVariant() {
    }

    public static RequirementsVariant fromJson(String json) {
        return new Gson().fromJson(json, RequirementsVariant.class);
    }

    public RequirementsVariant(String title, String description) {
        this.title = title;
        this.description = description;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
