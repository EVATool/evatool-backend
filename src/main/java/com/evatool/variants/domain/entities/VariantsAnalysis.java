package com.evatool.variants.domain.entities;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "VARIANT_ANALYSIS")
@Getter
public class VariantsAnalysis extends RepresentationModel<VariantsAnalysis> {


    @Id
    private UUID analysisId;

    public VariantsAnalysis(UUID id){
        this.analysisId = id;
    }

    public VariantsAnalysis(){

    }
}
