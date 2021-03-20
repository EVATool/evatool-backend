package com.evatool.analysis.domain.repository;

import com.evatool.analysis.domain.model.AnalysisImpacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnalysisImpactRepository extends JpaRepository<AnalysisImpacts, UUID> {


}
