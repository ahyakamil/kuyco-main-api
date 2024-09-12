package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateTransactionDto;
import com.kuyco.main_api.dto.HistoryFilterDto;
import com.kuyco.main_api.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    void create(Long customerId, CreateTransactionDto createTransactionDto);
    Page<TransactionDto> getHistory(Long customerId, HistoryFilterDto historyFilterDto, Pageable pageable);
    TransactionDto getById(Long id);
}
