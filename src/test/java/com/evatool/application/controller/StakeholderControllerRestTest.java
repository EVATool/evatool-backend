package com.evatool.application.controller;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.domain.entity.Stakeholder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class StakeholderControllerRestTest extends CrudControllerRestTest<Stakeholder, StakeholderDto> implements FindByAnalysisControllerRestTest {

    @Test
    void testGetStakeholderLevels() {
        // given

        // when
        var stakeholderLevels = rest.getForEntity(UriUtil.STAKEHOLDERS_LEVELS, StakeholderLevel[].class);

        // then
        assertThat(stakeholderLevels.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stakeholderLevels.getBody()).hasSize(StakeholderLevel.values().length);
        assertThat(stakeholderLevels.getBody()).isEqualTo(StakeholderLevel.values());
    }

    @Test
    void testGetStakeholderPriorities() {
        // given

        // when
        var stakeholderPriorities = rest.getForEntity(UriUtil.STAKEHOLDERS_PRIORITIES, StakeholderPriority[].class);

        // then
        assertThat(stakeholderPriorities.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stakeholderPriorities.getBody()).hasSize(StakeholderPriority.values().length);
        assertThat(stakeholderPriorities.getBody()).isEqualTo(StakeholderPriority.values());
    }
}
