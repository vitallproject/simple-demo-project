package com.vital.validator;

import com.vital.dto.CreateUserDto;
import com.vital.entity.Gender;
import com.vital.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto> {

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();
        if (!isValidEmailAddress(createUserDto.getEmail())) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (!isValidName(createUserDto.getName())) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (!LocalDateFormatter.isValid(createUserDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (createUserDto.getPassword().equals("") || createUserDto.getPassword().contains(" ")) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        if (Gender.find(createUserDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        return validationResult;
    }

    public boolean isValidEmailAddress(String email) { return email.matches(".+@.+\\..+"); }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
