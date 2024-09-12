package com.kuyco.main_api.dto;

import com.kuyco.main_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateTransactionDto {
    @NotNull(message = ErrorMessage.ITEMS_CANNOT_BE_NULL)
    private List<CreateTransactionItemDto> items;
}
