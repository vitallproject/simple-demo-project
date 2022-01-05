package com.vital.mapper;

import com.vital.dto.UserDto;
import com.vital.entity.User;

public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .image(object.getImage())
                .birthday(object.getBirthday())
                .email(object.getEmail())
                .gender(object.getGender())
                .build();
    }
}