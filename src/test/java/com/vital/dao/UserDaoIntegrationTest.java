package com.vital.dao;

import com.vital.entity.Gender;
import com.vital.entity.User;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserDaoIntegrationTest {

    private UserDao userDao = new UserDao();

    private static final User USER = User.builder()
            .id(1)
            .name("valid")
            .password("valid")
            .birthday(LocalDate.of(2021, 12, 12))
            .image("referenceToCloud")
            .gender(Gender.FEMALE)
            .email("valid@valid.com").build();

    private static final User USER2 = User.builder()
            .name("fake")
            .password("fake")
            .birthday(LocalDate.of(2021, 12, 12))
            .image("referenceToCloud")
            .gender(Gender.FEMALE)
            .email("fake@fake.com").build();

    @Test
    void shouldSaveAndDeleteUser() {
        var user = userDao.save(USER2);
        var delete = userDao.delete(USER2.getId());

        assertThat(user).isEqualTo(USER2);
        assertThat(delete).isTrue();
    }

    @Test
    void findByEmailAndPasswordTest() {
        var mayBeUser = userDao.findByEmailAndPassword(USER.getEmail(), USER.getPassword());

        assertThat(mayBeUser).isEqualTo(Optional.of(USER));
    }
}
