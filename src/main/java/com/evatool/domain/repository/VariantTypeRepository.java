package com.evatool.domain.repository;

import com.evatool.domain.entity.VariantType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VariantTypeRepository extends CrudRepository<VariantType, UUID>, FindByAnalysisRepository<VariantType> {
}
