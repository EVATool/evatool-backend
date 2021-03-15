package com.evatool.variants.entities;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "VARIANT_ANALYSIS")
@Getter
public class VariantsAnalysis extends RepresentationModel<VariantsAnalysis> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public VariantsAnalysis(UUID id){
        this.id = id;
    }

    public VariantsAnalysis(){

    }
}
