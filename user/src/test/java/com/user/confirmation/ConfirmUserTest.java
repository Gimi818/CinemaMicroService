package com.user.confirmation;

import com.user.user.AccountType;
import com.user.user.User;
import com.user.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {"confirmation.link=http://localhost:8090/api/v1/users/confirm?token="})
class ConfirmUserTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ConfirmUser confirmUser;

    @Value("${confirmation.link}")
    private String confirmationLink;

    @Test
    @DisplayName("Should generate token")
    public void should_generate_token() {
        String token = confirmUser.generateConfirmationToken();
        assertEquals(36, token.length());
    }


//    @Test
//    @DisplayName("Should generate confirmation email")
//    public void should_generate_confirmation_email() {
//
//        User user = new User();
//        user.setConfirmationToken("test_token");
//        String generateConfirmationLink = confirmUser.generateConfirmationLink(user);
//        assertEquals("http://localhost:8090/api/v1/users/confirm?token=test_token", generateConfirmationLink);
//    }


    @Test
    @DisplayName("Should confirm account")
    public void should_confirm_account() {
        User user = new User();
        user.setConfirmationToken("test_token");

        when(userRepository.findByConfirmationToken("test_token")).thenReturn(Optional.of(user));

        confirmUser.confirmUserAccount("test_token");

        assertEquals(AccountType.ACTIVE, user.getAccountType());
        assertEquals(null, user.getConfirmationToken());

        verify(userRepository, times(1)).save(user);
    }


}
