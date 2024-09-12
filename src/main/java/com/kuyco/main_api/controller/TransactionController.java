package com.kuyco.main_api.controller;


import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.dto.*;
import com.kuyco.main_api.helper.AuthHelper;
import com.kuyco.main_api.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuthHelper authHelper;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> create(@RequestBody CreateTransactionDto createTransactionDto, HttpServletRequest request) {
        transactionService.create(authHelper.getAccount(request).getCustomer().getId(), createTransactionDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseWrapper<Page<TransactionDto>>> getHistory(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                            @RequestParam(required = false) Long itemCategory,
                                                                            Pageable pageable,
                                                                            HttpServletRequest request) {
        HistoryFilterDto historyFilterDto = new HistoryFilterDto();
        historyFilterDto.setStartDate(startDate);
        historyFilterDto.setEndDate(endDate);
        historyFilterDto.setItemCategoryId(itemCategory);

        Page<TransactionDto> transactions = transactionService.getHistory(authHelper.getAccount(request).getCustomer().getId(), historyFilterDto, pageable);
        return new ResponseWrapper<Page<TransactionDto>>().buildResponseOk(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<TransactionDto>> getById(@PathVariable("id") Long id) {
        TransactionDto transaction = transactionService.getById(id);
        return new ResponseWrapper<TransactionDto>().buildResponseOk(transaction);
    }
}
