package com.evatool.variants.application.dto;

import com.evatool.variants.domain.entities.Variant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;

import java.util.UUID;

@ApiModel(value = "Variants")
@Getter
@Setter
public class VariantDto {

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID id;

    @ApiModelProperty(example = "VAR1")
    private String guiId;

    @ApiModelProperty(example = "Own Home")
    private String title;

    @ApiModelProperty(example = "Person suffering from dementia lives in his/her own home (e.g. appartment or house)")
    private String description;

    @ApiModelProperty(dataType="List", example = "[{id: \"f33c6fa8-1697-11ea-8d71-362b9e155667\", guiId: VAR2, title: \"Admin Contact\", description: \"ILA is administered by a contact person\", subVariant: [], analysisId:f33c6fa8-1697-11ea-8d71-362b9e155667, archived: false}]")
    private CollectionModel<Variant> subVariant;

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID analysisId;

    @ApiModelProperty(example = "false")
    private Boolean archived = false;
}
