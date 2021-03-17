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

@Entity(name = "Var_VARIANT")
@Getter
@Setter
public class Variant extends RepresentationModel<Variant> {

    @ApiModelProperty(notes = "Uuid of a Variant", name = "uuid", required = true)
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ApiModelProperty(notes = "Title of a Variant", name = "title", required = true)
    private String title;

    @ApiModelProperty(notes = "SubVariant of a Variant", name = "subVariant", required = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Variant> subVariant;

    @ApiModelProperty(notes = "Description of a Variant", name = "description", required = true)
    private String description;

    @ApiModelProperty(notes = "Potential flag of a Variant", name = "potentialFlag", required = false)
    private Boolean stFlagsPot;

    @ApiModelProperty(notes = "Reality flag of a Variant", name = "realityFlag", required = false)
    private Boolean stFlagsReal;

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
                ", stFlagsPot=" + stFlagsPot +
                ", stFlagsReal=" + stFlagsReal +
                ", variantsAnalyses=" + variantsAnalysis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Variant variant = (Variant) o;
        return Objects.equals(id, variant.id) && Objects.equals(title, variant.title) && Objects.equals(subVariant, variant.subVariant) && Objects.equals(description, variant.description) && Objects.equals(stFlagsPot, variant.stFlagsPot) && Objects.equals(stFlagsReal, variant.stFlagsReal) && Objects.equals(variantsAnalysis, variant.variantsAnalysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, subVariant, description, stFlagsPot, stFlagsReal, variantsAnalysis);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
