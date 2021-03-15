package com.evatool.variants.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
@Setter
public class VariantDto {

    private UUID uuid;
    private String title;
    private String description;
    private boolean stFlagsPot;
    private boolean stFlagsReal;
    private CollectionModel<Variant> subVariant;
    private UUID analysesId;
}