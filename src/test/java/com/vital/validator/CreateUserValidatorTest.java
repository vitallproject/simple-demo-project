package com.vital.validator;

import com.vital.dto.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserValidatorTest {

    private CreateUserValidator createUserValidator = new CreateUserValidator();

    @Test
    void validationResultShouldHasSizeIfUserIsInvalid() {
        var inValidCreateUserDto = CreateUserDto.builder().name("1").password(" ").birthday("invalid").gender("invalid").email("invalid").build();

        var validationResult = createUserValidator.isValid(inValidCreateUserDto);

        assertThat(validationResult.getErrors()).hasSize(5);
    }

    @Test
    void validationResultIsEmptyIfUserIsValid() {
        var createUserDto = CreateUserDto.builder().name("valid").password("valid").birthday("2021-12-12").gender("FEMALE").email("valid@valid.com").build();

        var validationResult = createUserValidator.isValid(createUserDto);

        assertThat(validationResult.getErrors()).isEmpty();
    }
}