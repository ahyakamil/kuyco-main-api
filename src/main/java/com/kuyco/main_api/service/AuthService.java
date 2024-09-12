package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.*;

public interface AuthService {
    AuthResponseDto login(LoginDto loginDto);
    AuthResponseDto refreshToken(RefreshTokenDto refreshTokenDto);
    void register(RegisterDto registerDto);
}
