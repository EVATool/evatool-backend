package com.evatool.analysis.model.repo;

import com.evatool.analysis.common.TestDataGenerator;
import com.evatool.analysis.model.User;
import com.evatool.analysis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepoTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByIdExistingUser() {

        // given
        User user = TestDataGenerator.getUser();
        userRepository.save(user);

        // when
        User userFound = userRepository.findById(user.getUserId()).orElse(null);

        // then
        assertThat(userFound.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void testSaveInsertedUserIdIsNotNull() {
        // given
        User user = TestDataGenerator.getUser();

        // when
        userRepository.save(user);

        // then
        assertThat(user.getUserId()).isNotNull();
    }

    @Test
    public void testSaveInsertedUserIdIsUuid() {
        // given
        User user = TestDataGenerator.getUser();

        // when
        userRepository.save(user);

        // then
        UUID.fromString(user.getUserId().toString());
        assertThat(user.getUserId()).isNotNull();

    }

    @Test
    public void testDeleteUser_ReturnNull() {
        // given
        User user = TestDataGenerator.getUser();
        userRepository.save(user);

        // when
        userRepository.delete(user);
        User userFound = userRepository.findById(user.getUserId()).orElse(null);

        // then
        assertThat(userFound).isNull();
    }




}
