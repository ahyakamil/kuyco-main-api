package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemDto {
    @NotBlank(message = ErrorMessage.NAME_CANNOT_BE_BLANK)
    private String name;

    @Min(value = 1, message = ErrorMessage.PRICE_MIN)
    @NotNull(message = ErrorMessage.PRICE_CANNOT_BE_NULL)
    private Double price;

    @NotNull(message = ErrorMessage.CATEGORY_ID_CANNOT_BE_NULL)
    private Long categoryId;
}
