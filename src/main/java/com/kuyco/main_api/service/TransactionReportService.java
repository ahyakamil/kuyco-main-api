package com.kuyco.main_api.service;

import com.kuyco.main_api.domain.Transaction;
import com.kuyco.main_api.dto.TransactionDto;

public interface TransactionReportService {
    void produce(Transaction transaction);
}
