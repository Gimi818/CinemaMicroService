package com.user.user;

import com.user.user.dto.CreatedUserDto;
import com.user.user.dto.UserRequestDto;
import com.user.user.dto.UserResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-04T18:33:14+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
class UserMapperImpl implements UserMapper {

    @Override
    public User dtoToEntity(UserRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( requestDto.firstName() );
        user.lastName( requestDto.lastName() );
        user.email( requestDto.email() );
        user.password( requestDto.password() );

        return user.build();
    }

    @Override
    public UserResponseDto entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        AccountType accountType = null;

        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        accountType = user.getAccountType();

        UserResponseDto userResponseDto = new UserResponseDto( id, firstName, lastName, email, accountType );

        return userResponseDto;
    }

    @Override
    public CreatedUserDto createdEntityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String email = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();

        CreatedUserDto createdUserDto = new CreatedUserDto( firstName, lastName, email );

        return createdUserDto;
    }
}
