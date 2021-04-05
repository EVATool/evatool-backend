package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Impact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpactRepository extends CrudRepository<Impact, UUID> {

    List<Impact> findAllByAnalysisId(UUID analysisId);

    List<Impact> findAllByStakeholderId(UUID stakeholderId); // TODO tests

    List<Impact> findAllByValueEntityId(UUID valueId); // TODO tests

}
