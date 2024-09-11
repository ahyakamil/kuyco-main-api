package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.dto.CreateCustomerDto;
import com.kuyco.main_api.dto.CustomerDto;
import com.kuyco.main_api.dto.ItemDto;
import com.kuyco.main_api.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public void create(CreateCustomerDto createDto) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Page<CustomerDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public ItemDto getById(Long id) {
        return null;
    }

    @Override
    public void updateById(Long id, CreateCustomerDto updateDto) {

    }
}
