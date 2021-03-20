package com.evatool.requirements.domain.repository;

import com.evatool.requirements.domain.entity.RequirementDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequirementDimensionRepository extends JpaRepository<RequirementDimension, UUID> {

}
