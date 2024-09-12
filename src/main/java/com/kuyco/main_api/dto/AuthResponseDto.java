package com.kuyco.main_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String token;
    private String refreshToken;
}
