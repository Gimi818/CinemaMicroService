package com.user.user;

import com.user.confirmation.ConfirmUser;
import com.user.user.dto.CreatedUserDto;
import com.user.user.dto.UserRequestDto;
import com.user.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.user.user.UserController.Routes.REGISTRATION;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final ConfirmUser confirmUser;

    @PostMapping(REGISTRATION)
    public ResponseEntity<CreatedUserDto> registration(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(service.registration(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(Routes.CONFIRM)
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String token) {
        confirmUser.confirmUserAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Your account has been confirmed.");
    }

    @GetMapping(Routes.FIND_USER_BY_ID)
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = service.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }


    static final class Routes {
        static final String ROOT = "/api/v1/users";
        static final String REGISTRATION = ROOT + "/registration";
        static final String CONFIRM = ROOT + "/confirm";
        static final String FIND_USER_BY_ID = ROOT + "/{id}";

    }
}
