package com.vital.service;

import com.vital.dao.UserDao;
import com.vital.dto.CreateUserDto;
import com.vital.dto.UserDto;
import com.vital.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.vital.mapper.CreateUserMapper;
import com.vital.mapper.UserMapper;
import com.vital.validator.CreateUserValidator;

import java.util.Optional;

@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ImageService imageService;
    private final CreateUserMapper createUserMapper;
    private final CreateUserValidator createUserValidator;
    private final UserMapper userMapper;

    public Optional<UserDto> login(String email, String password) {
        //validation
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    @SneakyThrows
    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
        userDao.save(userEntity);

        return userEntity.getId();
    }

    public boolean delete(Integer id) {
        return userDao.delete(id);
    }
}