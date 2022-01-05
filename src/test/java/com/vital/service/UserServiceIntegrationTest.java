package com.vital.service;

import com.vital.dao.UserDao;
import com.vital.dto.CreateUserDto;
import com.vital.dto.UserDto;
import com.vital.entity.Gender;
import com.vital.entity.User;
import com.vital.exception.ValidationException;
import com.vital.mapper.CreateUserMapper;
import com.vital.mapper.UserMapper;
import com.vital.validator.CreateUserValidator;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@ExtendWith(MockitoExtension.class)
@Tag("userServiceIntegration")
class UserServiceIntegrationTest {

    @Spy
    private UserDao userDao;
    @Spy
    private CreateUserValidator createUserValidator;
    @Spy
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    private static final User USER = User.builder()
            .name("valid")
            .email("valid@valid.com")
            .birthday(LocalDate.of(2021, 12, 12))
            .password("valid")
            .gender(Gender.FEMALE)
            .image("referenceToCloud")
            .build();

    private static final UserDto USER_DTO = UserDto.builder()
            .id(1)
            .name("valid")
            .email("valid@valid.com")
            .birthday(LocalDate.of(2021, 12, 12))
            .gender(Gender.FEMALE)
            .image("referenceToCloud").build();

    @Test
    void checkLoginFunctionalityPerformance() {
        assertTimeout(Duration.ofMillis(300L), () -> userService.login(USER.getEmail(), USER.getPassword()));
    }

    @ParameterizedTest(name = "{argumentsWithNames}")
    @MethodSource("getArgumentsForLoginTest")
    void loginParameterizedTest(String email, String password, Optional<UserDto> userDto) {
        var mayBeUser = userService.login(email, password);

        assertThat(mayBeUser).isEqualTo(userDto);
    }

    static Stream<Arguments> getArgumentsForLoginTest() {
        return Stream.of(
                Arguments.of("valid@valid.com", "valid", Optional.of(USER_DTO)),
                Arguments.of("valid", "dummy", Optional.empty()),
                Arguments.of("dummy", "valid", Optional.empty()));
    }

    @ParameterizedTest(name = "{argumentsWithNames}")
    @MethodSource("getArgumentsForValidationExceptionTest")
    void throwValidationException_IfUserForCreate_IsNotValid(CreateUserDto createUserDto) {
        assertThrows(ValidationException.class, () -> userService.create(createUserDto));
    }

    static Stream<Arguments> getArgumentsForValidationExceptionTest() {
        return Stream.of(
                Arguments.of(CreateUserDto.builder().name("1").password("valid").birthday("2021-12-12").gender("FEMALE").email("valid@valid.com").build()),
                Arguments.of(CreateUserDto.builder().name("valid").password("").birthday("2021-12-12").gender("FEMALE").email("valid@valid.com").build()),
                Arguments.of(CreateUserDto.builder().name("valid").password("valid").birthday("invalid").gender("FEMALE").email("valid@valid.com").build()),
                Arguments.of(CreateUserDto.builder().name("valid").password("valid").birthday("2021-12-12").gender("invalid").email("valid@valid.com").build()),
                Arguments.of(CreateUserDto.builder().name("valid").password("valid").birthday("2021-12-12").gender("FEMALE").email("invalid").build()));

    }
}

