package com.evatool.variants.application.dto;

import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.common.error.exceptions.IllegalAnalysisException;
import com.evatool.variants.common.error.exceptions.VariantNotArchivedException;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.entities.VariantsRequirements;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VariantMapper {

    @Autowired
    VariantRepository variantRepository;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Autowired
    VariantRequirementsRepository variantRequirementsRepository;


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
            variant.setSubVariant(new ArrayList<>(variantDto.getSubVariant().getContent()));
        }
        variant.setDescription(variantDto.getDescription());
        Optional<VariantsAnalysis> variantsAnalysis = variantsAnalysisRepository.findById(variantDto.getAnalysisId());
        if (variantsAnalysis.isEmpty()) {
            throw new IllegalAnalysisException(variantDto.getAnalysisId().toString());
        }
        variantsAnalysis.ifPresent(variant::setVariantsAnalysis);
        if (variantDto.getGuiId() == null || variantDto.getGuiId().equals("")) variant.setGuiId(generateGuiId(variantDto.getAnalysisId()));
        if (variantDto.getArchived()){
            if (checkIfArchivable(variantDto.getId())) {
                variant.setArchived(variantDto.getArchived());
            } else {
                throw new VariantNotArchivedException();
            }
        }
        return variant;
    }



    private String generateGuiId(UUID analysisId) {
        if (analysisId == null){
            throw new IllegalArgumentException();
        }
        List<Variant> variants = variantRepository.findAll();
        variants.removeIf(variant -> !variant.getVariantsAnalysis().getAnalysisId().equals(analysisId));
        return String.format("VAR%d", variants.size() + 1);
    }

    private Boolean checkIfArchivable(UUID variantid) {
        List<VariantsRequirements> requirements = variantRequirementsRepository.findAll();
        requirements.forEach(requirement -> {
            requirement.getVariants().forEach(requirementVariant -> {
                if (requirementVariant.getId() == variantid) requirements.remove(requirement);
            });
        });
        return requirements.isEmpty();
    }

}
