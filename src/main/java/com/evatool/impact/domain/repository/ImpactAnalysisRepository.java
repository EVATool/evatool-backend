package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.ImpactAnalysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImpactAnalysisRepository extends CrudRepository<ImpactAnalysis, UUID> {

}
