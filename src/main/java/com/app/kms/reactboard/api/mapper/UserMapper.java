package com.app.kms.reactboard.api.mapper;

import com.ivanfranchin.movieapi.model.User;
import com.ivanfranchin.movieapi.rest.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}