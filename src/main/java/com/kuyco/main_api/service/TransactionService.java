package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateTransactionDto;

public interface TransactionService {
    void create(Long customerId, CreateTransactionDto createTransactionDto);
}
