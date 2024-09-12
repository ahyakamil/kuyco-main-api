package com.kuyco.main_api.controller;


import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.dto.AuthResponseDto;
import com.kuyco.main_api.dto.LoginDto;
import com.kuyco.main_api.dto.RefreshTokenDto;
import com.kuyco.main_api.dto.RegisterDto;
import com.kuyco.main_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<AuthResponseDto>> register(@RequestBody LoginDto loginDto) {
        AuthResponseDto authResponseDto = authService.login(loginDto);
        return new ResponseWrapper<AuthResponseDto>().buildResponseOk(authResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<Void>> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseWrapper<AuthResponseDto>> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        AuthResponseDto authResponseDto = authService.refreshToken(refreshTokenDto);
        return new ResponseWrapper<AuthResponseDto>().buildResponseOk(authResponseDto);
    }
}
