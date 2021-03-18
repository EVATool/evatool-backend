package com.evatool.variants.application.dto;

import com.evatool.variants.domain.entities.Variant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;

import java.util.UUID;

@Getter
@Setter
public class VariantDto {

    private UUID uuid;
    private String title;
    private String description;
    private Boolean stFlagsPot;
    private Boolean stFlagsReal;
    private CollectionModel<Variant> subVariant;
    private UUID analysisId;
}
