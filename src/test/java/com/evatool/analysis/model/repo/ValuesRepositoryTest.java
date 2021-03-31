package com.evatool.analysis.model.repo;

import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static com.evatool.analysis.common.TestDataGenerator.createDummyValue;

@DataJpaTest
class ValuesRepositoryTest {

    @Autowired
    private ValueRepository valueRepository;

    @Test
    void testFindAllByType_ExistingValues_ReturnValueByType() {
        // given
        int n_socialValues = 3;
        for (int i = 0; i < n_socialValues; i++) {
            var socialValues = createDummyValue();
            socialValues.setType(ValueType.SOCIAL);
            valueRepository.save(socialValues);
        }

        int n_economicValues = 4;
        for (int i = 0; i < n_economicValues; i++) {
            var economicValue = createDummyValue();
            economicValue.setType(ValueType.ECONOMIC);
            valueRepository.save(economicValue);
        }

        // when
        var socialValue = valueRepository.findAllByType(ValueType.SOCIAL);
        var economicValue = valueRepository.findAllByType(ValueType.ECONOMIC);

        // then
        assertThat(socialValue.size()).isEqualTo(n_socialValues);
        assertThat(economicValue.size()).isEqualTo(n_economicValues);
    }
}
