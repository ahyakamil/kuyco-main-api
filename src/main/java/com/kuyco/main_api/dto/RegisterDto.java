package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    @NotBlank(message = ErrorMessage.NAME_CANNOT_BE_BLANK)
    @Size(max = 50, message = ErrorMessage.NAME_CANNOT_BE_GREATER_THAN)
    private String name;

    @NotBlank(message = ErrorMessage.EMAIL_CANNOT_BE_BLANK)
    @Size(max = 50, message = ErrorMessage.EMAIL_CANNOT_BE_GREATER_THAN)
    @Email(message = ErrorMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ErrorMessage.PASSWORD_CANNOT_BE_BLANK)
    @Size(max = 50, message = ErrorMessage.PASSWORD_CANNOT_BE_GREATER_THAN)
    private String password;
}
