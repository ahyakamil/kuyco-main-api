package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Customer;
import com.kuyco.main_api.domain.Item;
import com.kuyco.main_api.domain.Transaction;
import com.kuyco.main_api.dto.CreateTransactionDto;
import com.kuyco.main_api.dto.CreateTransactionItemDto;
import com.kuyco.main_api.dto.ItemDto;
import com.kuyco.main_api.exception.UnprocessableEntityException;
import com.kuyco.main_api.repository.CustomerRepository;
import com.kuyco.main_api.repository.ItemRepository;
import com.kuyco.main_api.repository.TransactionRepository;
import com.kuyco.main_api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Validation validation;

    @Transactional
    @Override
    public void create(Long customerId, CreateTransactionDto createTransactionDto) {
        validation.validate(createTransactionDto);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException(ErrorMessage.CUSTOMER_NOT_FOUND));
        List<Item> items = itemRepository.findAllById(createTransactionDto.getItems().stream().map(CreateTransactionItemDto::getId).toList());

        Double amount = 0.0;
        for (Item item : items) {
            amount += item.getPrice();
        }

        if(amount > 0) {
            Double oldBalance = customer.getBalance();
            Double newBalance = oldBalance - amount;

            if(newBalance < 0) {
                throw new UnprocessableEntityException(ErrorMessage.INSUFFICIENT_BALANCE);
            }

            Transaction transaction = new Transaction();
            transaction.setCustomer(customer);
            transaction.setItems(items);
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAmount(amount);
            transaction.setCustomerChange(amount);
            transaction.setCustomerOldBalance(oldBalance);
            transaction.setCustomerNewBalance(newBalance);

            transactionRepository.save(transaction);

            customer.setBalance(newBalance);
            customerRepository.save(customer);
        } else {
            throw new UnprocessableEntityException(ErrorMessage.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
        }
    }
}
