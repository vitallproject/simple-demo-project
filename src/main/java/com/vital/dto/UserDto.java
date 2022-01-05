package com.vital.dto;

import com.vital.entity.Gender;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {

    Integer id;
    String name;
    LocalDate birthday;
    String email;
    String image;
    Gender gender;
}
