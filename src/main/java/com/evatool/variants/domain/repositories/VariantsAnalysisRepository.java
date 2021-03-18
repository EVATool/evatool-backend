package com.evatool.variants.domain.repositories;

import com.evatool.variants.domain.entities.VariantsAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VariantsAnalysisRepository extends JpaRepository<VariantsAnalysis, UUID> {

}
