package com.evatool.variants.application.dto;

import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.common.error.exceptions.IllegalAnalysisException;
import com.evatool.variants.domain.entities.*;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VariantMapper {
    //todo error handling
    @Autowired
    VariantController variantController;
    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    public List<VariantDto> mapAll(List<Variant> variantList) {
        List<VariantDto> variantDtoList = new ArrayList<>();
        for (Variant variant : variantList) {
            variantDtoList.add(toDto(variant));
        }
        return variantDtoList;
    }

    public VariantDto toDto(Variant variant) {

        VariantDto variantDto = new VariantDto();
        variantDto.setUuid(variant.getId());
        variantDto.setTitle(variant.getTitle());
        variantDto.setDescription(variant.getDescription());
        variantDto.setStFlagsPot(variant.isStFlagsPot());
        variantDto.setStFlagsReal(variant.isStFlagsReal());

        if (variant.getSubVariant() != null){
            Link subVariantLink = linkTo(methodOn(VariantController.class).getAllVariants()).withSelfRel();
            List<Variant> variantList = new ArrayList<>();
            variantList.addAll(variant.getSubVariant());
            CollectionModel<Variant> subVariantCollectionModel = CollectionModel.of(variantList);
            subVariantCollectionModel.add(subVariantLink);
        }

        variantDto.setAnalysesId(variant.getVariantsAnalyses().getAnalysisId());

        return variantDto;
    }

    public Variant fromDto(VariantDto variantDto) {
        Variant variant = new Variant();
        variant.setId(variantDto.getUuid());
        variant.setTitle(variantDto.getTitle());
        if(variantDto.getSubVariant() != null) {
            variant.setSubVariant(variantDto.getSubVariant().getContent().stream().collect(Collectors.toList()));
        }
        variant.setDescription(variantDto.getDescription());
        variant.setStFlagsPot(variantDto.isStFlagsPot());
        variant.setStFlagsReal(variantDto.isStFlagsReal());
        Optional<VariantsAnalysis> variantsAnalysis = variantsAnalysisRepository.findById(variantDto.getAnalysesId());
        if(variantsAnalysis.isEmpty())
        {
            throw new IllegalAnalysisException();
        }

        variantsAnalysis.ifPresent(variant::setVariantsAnalyses);
        return variant;
    }

}
