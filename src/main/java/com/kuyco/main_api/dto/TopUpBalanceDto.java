package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopUpBalanceDto {
    @Min(value = 1, message = ErrorMessage.TOP_UP_BALANCE_MIN)
    private Double amount;
}
