package com.evatool.requirements.domain.repository;

import com.evatool.requirements.domain.entity.RequirementIdPerAnalysis;
import com.evatool.requirements.domain.entity.RequirementsAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequirementIdPerAnalysisRepository extends JpaRepository<RequirementIdPerAnalysis, UUID> {

    RequirementIdPerAnalysis findByAnalysisId(String id);
}
