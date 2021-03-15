package com.evatool.variants.domain.entities;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "VARIANT")
@Getter
@Setter
public class Variant extends RepresentationModel<Variant> {

    @ApiModelProperty(notes = "Uuid of a Variant", name = "uuid", required = true)
    @Id
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
    private boolean stFlagsPot;

    @ApiModelProperty(notes = "Reality flag of a Variant", name = "realityFlag", required = false)
    private boolean stFlagsReal;

    @ApiModelProperty(notes = "Analysis of a Variant", name = "analysis", required = true)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private VariantsAnalysis variantsAnalyses;

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subVariant=" + subVariant +
                ", description='" + description + '\'' +
                ", stFlagsPot=" + stFlagsPot +
                ", stFlagsReal=" + stFlagsReal +
                ", variantsAnalyses=" + variantsAnalyses +
                '}';
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
