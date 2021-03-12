package com.evatool.analysis.repository;

import com.evatool.analysis.model.Stakeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StakeholderRepository extends JpaRepository<Stakeholder, UUID> {

}
