package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.ImpactRequirement;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImpactRequirementRepository extends CrudRepository<ImpactRequirement, UUID> {
}
