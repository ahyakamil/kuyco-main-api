package com.kuyco.main_api.helper;

import com.kuyco.main_api.config.JwtUtil;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Account;
import com.kuyco.main_api.exception.UnauthorizedException;
import com.kuyco.main_api.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Account getAccount(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        return accountRepository.findByEmail(username).orElseThrow(() -> new UnauthorizedException(ErrorMessage.INVALID_CREDENTIAL));
    }
}
