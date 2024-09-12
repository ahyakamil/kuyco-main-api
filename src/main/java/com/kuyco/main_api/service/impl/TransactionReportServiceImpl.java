package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.domain.Item;
import com.kuyco.main_api.domain.Transaction;
import com.kuyco.main_api.dto.TransactionDto;
import com.kuyco.main_api.dto.TransactionReportDto;
import com.kuyco.main_api.service.TransactionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TransactionReportServiceImpl implements TransactionReportService {
    @Value("${topic.transaction.report}")
    private String transactionReportTopic;

    @Autowired
    private KafkaTemplate<String, TransactionReportDto> kafkaTemplate;

    @Override
    public void produce(Transaction transaction) {
        TransactionReportDto reportDto = new TransactionReportDto();
        reportDto.setId(transaction.getId());
        reportDto.setCustomerId(transaction.getCustomer().getId());
        reportDto.setCustomerName(transaction.getCustomer().getName());
        reportDto.setCustomerChange(transaction.getCustomerChange());
        reportDto.setAmount(transaction.getAmount());
        reportDto.setCustomerOldBalance(transaction.getCustomerOldBalance());
        reportDto.setCustomerNewBalance(transaction.getCustomerNewBalance());
        reportDto.setItemNames(transaction.getItems().stream().map(Item::getName).collect(Collectors.toList()));
        kafkaTemplate.send(transactionReportTopic, reportDto);
    }
}
