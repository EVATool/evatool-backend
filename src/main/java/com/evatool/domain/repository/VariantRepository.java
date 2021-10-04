package com.evatool.domain.repository;

import com.evatool.domain.entity.Variant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VariantRepository extends CrudRepository<Variant, UUID>, FindByAnalysisRepository<Variant> {

    Iterable<Variant> findAllByVariantTypeId(UUID variantTypeId);

}
