package com.evatool.domain.repository;

import com.evatool.domain.entity.Requirement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequirementRepository extends CrudRepository<Requirement, UUID>, FindByAnalysisRepository<Requirement> {

    Iterable<Requirement> findAllByVariantsId(UUID variantId); // TODO tests

}
