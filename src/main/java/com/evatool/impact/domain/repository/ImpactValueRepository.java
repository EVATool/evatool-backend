package com.evatool.impact.domain.repository;

import com.evatool.impact.common.ImpactValueType;
import com.evatool.impact.domain.entity.ImpactValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpactValueRepository extends CrudRepository<ImpactValue, UUID> {

    List<ImpactValue> findAllByType(ImpactValueType type);

    List<ImpactValue> findAllByAnalysisId(UUID analysisId); // TODO Tests

}
