package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDto {
    @NotBlank(message = ErrorMessage.REFRESH_TOKEN_CANNOT_BE_BLANK)
    private String refreshToken;
}
