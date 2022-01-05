package com.vital.service;

import com.vital.dao.UserDao;
import com.vital.dto.CreateUserDto;
import com.vital.dto.UserDto;
import com.vital.entity.Gender;
import com.vital.entity.User;
import jakarta.servlet.http.Part;
import com.vital.mapper.CreateUserMapper;
import com.vital.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.vital.validator.CreateUserValidator;
import com.vital.validator.ValidationResult;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CreateUserValidator createUserValidator;
    @Mock
    private ImageService imageService;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    Part part;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<Integer> argumentCaptor;

    private static final UserDto USER_DTO = UserDto.builder()
            .id(1)
            .name("valid")
            .email("valid@valid.com")
            .birthday(LocalDate.of(2021, 12, 12))
            .gender(Gender.FEMALE)
            .image("referenceToCloud").build();

    private static final User USER = User.builder()
            .id(1)
            .name("valid")
            .password("valid")
            .birthday(LocalDate.of(2021, 12, 12))
            .image("referenceToCloud")
            .gender(Gender.FEMALE)
            .email("valid@valid.com").build();

    @Test
    void createFunctionalityTest() throws Exception {
        var validationResult = new ValidationResult();
        var createUserDto = CreateUserDto.builder()
                .name("valid")
                .password("valid")
                .birthday("2021-12-12")
                .gender("FEMALE")
                .image(part)
                .email("valid@valid.com").build();

        doReturn(validationResult).when(createUserValidator).isValid(createUserDto);
        doReturn(USER).when(createUserMapper).mapFrom(createUserDto);
        doReturn(USER).when(userDao).save(USER);

        var integer = userService.create(createUserDto);

        verify(imageService).upload(USER.getImage(), createUserDto.getImage().getInputStream());

        assertThat(integer).isEqualTo(USER.getId());
    }

    @Test
    void loginFunctionalityTest() {
        doReturn(Optional.of(USER)).when(userDao).findByEmailAndPassword(USER.getEmail(), USER.getPassword());
        doReturn(USER_DTO).when(userMapper).mapFrom(USER);

        var userDto = userService.login(USER.getEmail(), USER.getPassword());

        assertThat(userDto).isEqualTo(Optional.of(USER_DTO));
    }

    @Test
    void throwExceptionIfDatabaseIsNotAvailable() {
        doThrow(RuntimeException.class).when(userDao).delete(anyInt());

        assertThrows(RuntimeException.class, () -> userService.delete(USER.getId()));
    }

    @Test
    void shouldDeleteExistedUser() {
        doReturn(true).when(userDao).delete(anyInt());

        var result = userService.delete(USER.getId());

        verify(userDao).delete(argumentCaptor.capture()); //demo

        assertAll(
                () -> assertThat(argumentCaptor.getValue()).isEqualTo(USER.getId()),
                () -> assertThat(result).isTrue());
    }
}
