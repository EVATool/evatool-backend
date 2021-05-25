package com.evatool.domain.repository;

import com.evatool.domain.entity.Stakeholder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StakeholderRepository extends CrudRepository<Stakeholder, UUID>, FindByAnalysisRepository<Stakeholder> {

}
