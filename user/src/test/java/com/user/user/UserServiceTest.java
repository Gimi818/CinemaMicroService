package com.user.user;

import com.user.confirmation.ConfirmUser;
import com.user.user.dto.CreatedUserDto;
import com.user.user.dto.UserRequestDto;
import com.user.user.dto.UserResponseDto;
import com.user.user.encoder.PasswordEncoderService;
import com.user.user.exception.exceptions.AlreadyExistException;
import com.user.user.exception.exceptions.PasswordConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRequestDto userRequestDto;
    @Mock
    private UserResponseDto userResponseDto;
    @Mock
    private UserController controller;
    @Mock
    private ConfirmUser confirmUser;
    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private User user;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto("Adam", "Buu", "ab@o.com", "qwerty", "qwerty");
        user = new User(1L, "Adam", "Buu", "ab@o.com", "qwerty", AccountType.UNCONFIRMED, "janskdjnjnj");
        userResponseDto = new UserResponseDto(1L, "A", "B", "ab@o.com", AccountType.UNCONFIRMED);
    }

    @Test
    @DisplayName("Should save user")
    void should_save_user() {

        given(userRepository.save(userMapper.dtoToEntity(userRequestDto)))
                .willReturn(user);

        assertThat(userService.registration(userRequestDto))
                .isEqualTo(userMapper.createdEntityToDto(user));
    }

    @Test
    @DisplayName("Should find user by ID")
    void should_find_user_by_id() {
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        given(userMapper.entityToDto(user))
                .willReturn(userResponseDto);

        assertThat(userService.findUserById(id)).isEqualTo(userResponseDto);
    }

    @Test
    @DisplayName("Should throw NotSamePasswordException")
    void Should_throw_NotSamePasswordException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Ferdo", "john@example.com", "password1", "password2");

        // When & Then
        assertThrows(PasswordConflictException.class, () -> userService.passwordValidation(requestDto));
    }


    @Test
    @DisplayName("Should throw EmailAlreadyExistsException")
    void Should_throw_EmailAlreadyExistsException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Doe", "john@example.com", "password", "password");
        when(userRepository.existsByEmail(requestDto.email())).thenReturn(true);

        // When & Then
        assertThrows(AlreadyExistException.class, () -> userService.existByMail(requestDto));
    }

    @Test
    void registration() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "john@example.com", "password", "password");
        CreatedUserDto createdUserDto = new CreatedUserDto(1L, "John", "Doe", "john@example.com");

        when(userService.registration(userRequestDto)).thenReturn(createdUserDto);

        ResponseEntity<CreatedUserDto> response = controller.registration(userRequestDto);


        assertEquals(201, response.getStatusCodeValue());
        assertEquals("John", response.getBody().firstName());
    }

}
