package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Customer;
import com.kuyco.main_api.domain.Item;
import com.kuyco.main_api.domain.Transaction;
import com.kuyco.main_api.dto.*;
import com.kuyco.main_api.exception.BadRequestException;
import com.kuyco.main_api.exception.NotFoundException;
import com.kuyco.main_api.exception.UnprocessableEntityException;
import com.kuyco.main_api.repository.CustomerRepository;
import com.kuyco.main_api.repository.ItemRepository;
import com.kuyco.main_api.repository.TransactionRepository;
import com.kuyco.main_api.service.TransactionReportService;
import com.kuyco.main_api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private TransactionReportService transactionReportService;

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
            transactionReportService.produce(transaction);
        } else {
            throw new UnprocessableEntityException(ErrorMessage.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
        }
    }

    @Override
    public Page<TransactionDto> getHistory(Long customerId, HistoryFilterDto historyFilterDto, Pageable pageable) {
        Specification<Transaction> spec = Specification.where(null);

        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("customer").get("id"), customerId));

        if (historyFilterDto.getStartDate() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), historyFilterDto.getStartDate()));
        }

        if (historyFilterDto.getEndDate() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), historyFilterDto.getEndDate()));
        }

        if (historyFilterDto.getItemCategoryId() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join("items").join("category").get("id"), historyFilterDto.getItemCategoryId()));
        }

        if (historyFilterDto.getStartDate() != null && historyFilterDto.getEndDate() != null && historyFilterDto.getEndDate().isBefore(historyFilterDto.getStartDate())) {
            throw new BadRequestException(ErrorMessage.END_DATE_CANNOT_BE_BEFORE_START_DATE);
        }

        Page<Transaction> history = transactionRepository.findAll(spec, pageable);
        return history.map(transaction -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(transaction.getId());
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setCustomerChange(transaction.getCustomerChange());
            transactionDto.setCustomerOldBalance(transaction.getCustomerOldBalance());
            transactionDto.setCustomerNewBalance(transaction.getCustomerNewBalance());
            transactionDto.setTransactionDate(transaction.getTransactionDate());

            List<Item> items = transaction.getItems();
            List<ItemDto> itemDtos = new ArrayList<>();
            for (Item item : items) {
                ItemDto itemDto = new ItemDto();
                itemDto.setId(item.getId());
                itemDto.setName(item.getName());
                itemDto.setPrice(item.getPrice());

                ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
                itemCategoryDto.setId(item.getCategory().getId());
                itemCategoryDto.setName(item.getCategory().getName());
                itemDto.setCategory(itemCategoryDto);

                itemDtos.add(itemDto);
            }
            transactionDto.setItems(itemDtos);
            return transactionDto;
        });
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(NotFoundException::new);
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCustomerChange(transaction.getCustomerChange());
        transactionDto.setCustomerOldBalance(transaction.getCustomerOldBalance());
        transactionDto.setCustomerNewBalance(transaction.getCustomerNewBalance());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        List<Item> items = transaction.getItems();
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(item.getId());
            itemDto.setName(item.getName());
            itemDto.setPrice(item.getPrice());
            ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
            itemCategoryDto.setId(item.getCategory().getId());
            itemCategoryDto.setName(item.getCategory().getName());
            itemDto.setCategory(itemCategoryDto);
            itemDtos.add(itemDto);
        }
        transactionDto.setItems(itemDtos);
        return transactionDto;
    }
}
