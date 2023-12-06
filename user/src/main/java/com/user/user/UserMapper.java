package com.user.user;

import com.user.user.dto.CreatedUserDto;
import com.user.user.dto.UserRequestDto;
import com.user.user.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    User dtoToEntity(UserRequestDto requestDto);

    UserResponseDto entityToDto(User user);

    CreatedUserDto createdEntityToDto(User user);

}
