package com.evatool.domain.repository;

import com.evatool.domain.entity.RequirementDelta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequirementDeltaRepository extends CrudRepository<RequirementDelta, UUID>, FindByAnalysisRepository<RequirementDelta> {

    @Override
    @Query(value = "select rd "
            + "from requirement_delta rd "
            + "inner join requirement r on rd.requirement.id=r.id "
            + "where r.analysis.id=?1")
    Iterable<RequirementDelta> findAllByAnalysisId(UUID analysisId);

    Iterable<RequirementDelta> findAllByImpactId(UUID impactId);

    Iterable<RequirementDelta> findAllByRequirementId(UUID requirementId);

}
