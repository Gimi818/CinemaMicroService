package com.user.user;

import com.user.confirmation.ConfirmUser;
import com.user.user.dto.CreatedUserDto;
import com.user.user.dto.UserRequestDto;
import com.user.user.dto.UserResponseDto;
import com.user.user.encoder.PasswordEncoderService;
import com.user.user.exception.exceptions.AlreadyExistException;
import com.user.user.exception.exceptions.NotFoundException;
import com.user.user.exception.exceptions.PasswordConflictException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.user.user.UserService.ErrorMessages.*;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final ConfirmUser confirmUser;
    private final PasswordEncoderService passwordEncoderService;


    @Transactional
    public CreatedUserDto registration(UserRequestDto requestDto) {

        existByMail(requestDto);
        passwordValidation(requestDto);

        User user = createUser(requestDto);

        repository.save(user);

        log.info("Saved user {}", requestDto.email());

        confirmUser.sendConfirmationEmail(user);
        log.info("Sent confirmation email");

        return userMapper.createdEntityToDto(user);

    }

    private User createUser(UserRequestDto requestDto) {
        return User.builder()
                .lastName(requestDto.lastName())
                .firstName(requestDto.firstName())
                .password(passwordEncoderService.encodePassword(requestDto.password()))
                .accountType(AccountType.UNCONFIRMED)
                .confirmationToken(confirmUser.generateConfirmationToken())
                .email(requestDto.email())
                .build();
    }



    public UserResponseDto findUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID, id));
        log.info("Found user with ID {}", user.getId());
        return userMapper.entityToDto(user);
    }


    public void passwordValidation(UserRequestDto requestDto) {
        if (!requestDto.password().equals(requestDto.repeatedPassword())) {
            throw new PasswordConflictException(PASSWORD_CONFLICT);
        }
    }

    public void existByMail(UserRequestDto requestDto) {
        if (repository.existsByEmail(requestDto.email())) {
            throw new AlreadyExistException(EMAIL_ALREADY_TAKEN, requestDto.email());
        }
    }

    static final class ErrorMessages {
        static final String NOT_FOUND_BY_ID = "User with id %d not found";
        static final String EMAIL_ALREADY_TAKEN = "This email %s is already taken";
        static final String PASSWORD_CONFLICT = "The passwords given aren't the same ";

    }

}
