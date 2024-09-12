package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = ErrorMessage.EMAIL_CANNOT_BE_BLANK)
    private String email;

    @NotBlank(message = ErrorMessage.PASSWORD_CANNOT_BE_BLANK)
    private String password;
}
