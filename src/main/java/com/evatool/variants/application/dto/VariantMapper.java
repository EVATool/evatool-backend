package com.evatool.variants.application.dto;

import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.common.error.exceptions.IllegalAnalysisException;
import com.evatool.variants.domain.entities.*;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VariantMapper {
    @Autowired
    VariantController variantController;

    @Autowired
    VariantRepository variantRepository;

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
        variantDto.setId(variant.getId());
        variantDto.setGuiId(variant.getGuiId());
        variantDto.setTitle(variant.getTitle());
        variantDto.setDescription(variant.getDescription());
        if (variant.getSubVariant() != null) {
            Link subVariantLink = linkTo(methodOn(VariantController.class).getAllVariants()).withSelfRel();
            List<Variant> variantList = new ArrayList<>();
            variantList.addAll(variant.getSubVariant());
            CollectionModel<Variant> subVariantCollectionModel = CollectionModel.of(variantList);
            subVariantCollectionModel.add(subVariantLink);
        }
        variantDto.setAnalysisId(variant.getVariantsAnalysis().getAnalysisId());
        variantDto.setArchived(variant.getArchived());
        return variantDto;
    }

    public Variant fromDto(VariantDto variantDto) {
        Variant variant = new Variant();
        variant.setId(variantDto.getId());

        variant.setTitle(variantDto.getTitle());
        if (variantDto.getSubVariant() != null) {
            variant.setSubVariant(variantDto.getSubVariant().getContent().stream().collect(Collectors.toList()));
        }
        variant.setDescription(variantDto.getDescription());
        Optional<VariantsAnalysis> variantsAnalysis = variantsAnalysisRepository.findById(variantDto.getAnalysisId());
        if (variantsAnalysis.isEmpty()) {
            throw new IllegalAnalysisException(variantDto.getAnalysisId().toString());
        }
        variantsAnalysis.ifPresent(variant::setVariantsAnalysis);
        if(variantDto.getGuiId().equals(""))variant.setGuiId(generateGuiId(variantDto.getAnalysisId()));
        variant.setArchived(variantDto.getArchived());
        return variant;
    }


    private String generateGuiId(UUID analysisId){
        List<Variant> variants = variantRepository.findAll();
        variants.removeIf(variant -> !variant.getVariantsAnalysis().getAnalysisId().equals(analysisId));
        return String.format("VAR%d", variants.size()+ 1);
    }

}
