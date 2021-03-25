package com.evatool.impact.domain.repository;

import com.evatool.impact.common.DimensionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.evatool.impact.common.TestDataGenerator.createDummyDimension;
import static org.assertj.core.api.Assertions.assertThat;

class DimensionRepositoryTest extends RepositoryTest {

    @Autowired
    private DimensionRepository dimensionRepository;

    @Test
    void testFindAllByType_ExistingDimensions_ReturnDimensionsByType() {
        // given
        int n_socialDimensions = 3;
        for (int i = 0; i < n_socialDimensions; i++) {
            var socialDimension = createDummyDimension();
            socialDimension.setType(DimensionType.SOCIAL);
            dimensionRepository.save(socialDimension);
        }

        int n_economicDimensions = 4;
        for (int i = 0; i < n_economicDimensions; i++) {
            var economicDimension = createDummyDimension();
            economicDimension.setType(DimensionType.ECONOMIC);
            dimensionRepository.save(economicDimension);
        }

        // when
        var socialDimensions = dimensionRepository.findAllByType(DimensionType.SOCIAL);
        var economicDimension = dimensionRepository.findAllByType(DimensionType.ECONOMIC);

        // then
        assertThat(socialDimensions.size()).isEqualTo(n_socialDimensions);
        assertThat(economicDimension.size()).isEqualTo(n_economicDimensions);
    }
}
