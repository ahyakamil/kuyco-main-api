package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.JwtUtil;
import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Account;
import com.kuyco.main_api.domain.Customer;
import com.kuyco.main_api.dto.AuthResponseDto;
import com.kuyco.main_api.dto.LoginDto;
import com.kuyco.main_api.dto.RefreshTokenDto;
import com.kuyco.main_api.dto.RegisterDto;
import com.kuyco.main_api.exception.ConflictException;
import com.kuyco.main_api.exception.UnauthorizedException;
import com.kuyco.main_api.repository.AccountRepository;
import com.kuyco.main_api.repository.CustomerRepository;
import com.kuyco.main_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private Validation validation;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        validation.validate(loginDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        if (userDetails == null || !passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new UnauthorizedException(ErrorMessage.INVALID_CREDENTIAL);
        }

        String token = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(token);
        authResponseDto.setRefreshToken(refreshToken);
        return authResponseDto;
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenDto refreshTokenDto) {
        validation.validate(refreshTokenDto);
        try {
            String username = jwtUtil.extractUsername(refreshTokenDto.getRefreshToken());
            if (username != null && jwtUtil.validateToken(refreshTokenDto.getRefreshToken(), username)) {
                String token = jwtUtil.generateToken(username);
                String refreshToken = jwtUtil.generateRefreshToken(username);

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(token);
                authResponseDto.setRefreshToken(refreshToken);
                return authResponseDto;
            }
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
        throw new UnauthorizedException(ErrorMessage.INVALID_CREDENTIAL);
    }


    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        validation.validate(registerDto);

        if (accountRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST);
        }

        Customer customer = new Customer();
        customer.setEmail(registerDto.getEmail());
        customer.setName(registerDto.getName());

        customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(customer);
        account.setEmail(registerDto.getEmail());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        accountRepository.save(account);
    }
}
