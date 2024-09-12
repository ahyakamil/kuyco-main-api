package com.kuyco.main_api.controller;


import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.CreateTransactionDto;
import com.kuyco.main_api.helper.AuthHelper;
import com.kuyco.main_api.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
