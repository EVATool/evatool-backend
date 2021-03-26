package com.evatool.variants.domain.entities;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "VAR_VARIANT")
@Getter
@Setter
public class Variant extends RepresentationModel<Variant> {

    @ApiModelProperty(notes = "Uuid of a Variant", name = "uuid", required = true)
    @Id
    @Type(type= "uuid-char")
    @Column(columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ApiModelProperty(notes = "Gui ID", name = "Gui ID", required = true)
    private String guiId;

    @ApiModelProperty(notes = "Title of a Variant", name = "title", required = true)
    private String title;

    @ApiModelProperty(notes = "archive ", name = "archive", required = true)
    private Boolean archived = false;


    @ApiModelProperty(notes = "SubVariant of a Variant", name = "subVariant", required = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Variant> subVariant;

    @ApiModelProperty(notes = "Description of a Variant", name = "description", required = true)
    private String description;


    @ApiModelProperty(notes = "Analysis of a Variant", name = "analysis", required = true)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private VariantsAnalysis variantsAnalysis;

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subVariant=" + subVariant +
                ", description='" + description + '\'' +
                ", variantsAnalyses=" + variantsAnalysis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Variant variant = (Variant) o;
        return Objects.equals(id, variant.id) && Objects.equals(title, variant.title) && Objects.equals(subVariant, variant.subVariant) && Objects.equals(description, variant.description) && Objects.equals(variantsAnalysis, variant.variantsAnalysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, subVariant, description, variantsAnalysis);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
