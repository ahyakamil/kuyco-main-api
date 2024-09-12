package com.kuyco.main_api.dto;


import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemCategoryDto {
    @NotBlank(message = ErrorMessage.NAME_CANNOT_BE_BLANK)
    private String name;
}
