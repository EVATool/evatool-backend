package com.evatool.analysis.application.dto.mapper;

import com.evatool.analysis.application.dto.StakeholderDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StakeholderDTOTest {

    @Test
    void testSetRootEntityId_NullValue_ThrowException() {
        // given
        var stakeholderDto = new StakeholderDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholderDto.setRootEntityID(null));
    }

    @Test
    void testSetStakeholderName_NullValue_ThrowException() {
        // given
        var stakeholderDto = new StakeholderDTO();
        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholderDto.setStakeholderName(null));
    }

    @Test
    void testSetStakeholderLevel_NullValue_ThrowException() {
        // given
        var stakeholderDto = new StakeholderDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholderDto.setStakeholderLevel(null));
    }

    @Test
    void testSetStakeholderPriority_NullValue_ThrowException() {
        // given
        var stakeholderDto = new StakeholderDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholderDto.setPriority(null));

    }

    @Test
    void testSetStakeholderGuiId_NullValue_ThrowException() {
        // given
        var stakeholderDto = new StakeholderDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholderDto.setGuiId(null));

    }
}
