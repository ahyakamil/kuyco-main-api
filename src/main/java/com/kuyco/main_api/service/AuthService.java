package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);
}
