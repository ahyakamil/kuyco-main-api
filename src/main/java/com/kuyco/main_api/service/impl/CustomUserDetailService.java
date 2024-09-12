package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Account;
import com.kuyco.main_api.exception.UnauthorizedException;
import com.kuyco.main_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException(ErrorMessage.INVALID_CREDENTIAL));
        return User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .build();
    }
}
