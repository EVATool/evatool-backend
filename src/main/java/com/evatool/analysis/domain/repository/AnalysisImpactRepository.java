package com.evatool.analysis.domain.repository;

import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.AnalysisImpact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnalysisImpactRepository extends JpaRepository<AnalysisImpact, UUID> {


}
