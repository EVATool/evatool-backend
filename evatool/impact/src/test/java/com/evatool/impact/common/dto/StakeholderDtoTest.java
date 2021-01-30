package com.evatool.impact.common.dto;

import com.evatool.impact.common.mapper.StakeholderMapper;
import org.junit.jupiter.api.Test;

import static com.evatool.impact.persistence.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThat;

public class StakeholderDtoTest {
    @Test
    public void testToString_DefaultObject_DoNotThrowException() {
        // given
        var stakeholderMapper = new StakeholderMapper();
        var stakeholder = getStakeholder();

        // when
        var stakeholderDto = stakeholderMapper.toStakeholderDto(stakeholder);

        // then
        stakeholderDto.toString();
    }
}