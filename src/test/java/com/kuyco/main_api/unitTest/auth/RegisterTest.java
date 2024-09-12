package com.kuyco.main_api.unitTest.auth;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Account;
import com.kuyco.main_api.domain.Customer;
import com.kuyco.main_api.dto.RegisterDto;
import com.kuyco.main_api.exception.BadRequestException;
import com.kuyco.main_api.repository.AccountRepository;
import com.kuyco.main_api.repository.CustomerRepository;
import com.kuyco.main_api.service.AuthService;
import com.kuyco.main_api.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegisterTest {
    @Spy
    private Validation validation;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void given_valid_data_register_when_registered_then_success() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("hello1@gmail.com");
        registerDto.setPassword("hello123");
        registerDto.setName("hello1");

        authService.register(registerDto);
        Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any(Customer.class));
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
    }

    @Test
    public void given_invalid_data_register_invalid_email_when_registered_then_return_exception_bad_request() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("hello1gmail.com");
        registerDto.setPassword("hello123");
        registerDto.setName("hello1");

        assertThrows(BadRequestException.class, () -> authService.register(registerDto));
        try {
            authService.register(registerDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.EMAIL_INVALID));
        }
    }

    @Test
    public void given_invalid_data_register_email_blank_when_registered_then_return_exception_bad_request() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("");
        registerDto.setPassword("hello123");
        registerDto.setName("hello1");

        assertThrows(BadRequestException.class, () -> authService.register(registerDto));
        try {
            authService.register(registerDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.EMAIL_CANNOT_BE_BLANK));
        }

        registerDto.setEmail("              ");
        assertThrows(BadRequestException.class, () -> authService.register(registerDto));
        try {
            authService.register(registerDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.EMAIL_CANNOT_BE_BLANK));
        }

        registerDto.setEmail(null);
        assertThrows(BadRequestException.class, () -> authService.register(registerDto));
        try {
            authService.register(registerDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.EMAIL_CANNOT_BE_BLANK));
        }
    }
}
