package com.kuyco.main_api.dto;

import com.kuyco.main_api.domain.Item;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private CustomerDto customer;

    private List<Item> items;

    private LocalDateTime transactionDate;
    private Double amount;
    private Double customerChange;
    private Double customerOldBalance;
    private Double customerNewBalance;
}
