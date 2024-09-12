package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.AuthResponseDto;
import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.LoginDto;
import com.kuyco.main_api.dto.RegisterDto;

public interface AuthService {
    AuthResponseDto login(LoginDto loginDto);
    void register(RegisterDto registerDto);
}
