package com.evatool.variants.domain.repositories;

import com.evatool.variants.domain.entities.VariantsRequirements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VariantRequirementsRepository extends JpaRepository<VariantsRequirements, UUID> {

}
