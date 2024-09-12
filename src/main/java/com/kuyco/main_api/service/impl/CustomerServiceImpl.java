package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.domain.Customer;
import com.kuyco.main_api.dto.TopUpBalanceDto;
import com.kuyco.main_api.exception.ConflictException;
import com.kuyco.main_api.exception.NotFoundException;
import com.kuyco.main_api.repository.CustomerRepository;
import com.kuyco.main_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Validation validation;

    @Transactional
    @Override
    public void topUpBalance(Long customerId, TopUpBalanceDto topUpBalanceDto) {
        validation.validate(topUpBalanceDto);
        Customer customer = customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
        try {
            customer.setBalance(customer.getBalance() + topUpBalanceDto.getAmount());
            customerRepository.save(customer);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictException(e.getMessage());
        }
    }
}
