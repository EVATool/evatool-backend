
package com.evatool.requirements.domain.repository;

import com.evatool.requirements.domain.entity.Requirement;
import com.evatool.requirements.domain.entity.RequirementsAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, UUID> {

    Collection<Requirement> findByRequirementsAnalysis(RequirementsAnalysis requirementsAnalysis);
}

