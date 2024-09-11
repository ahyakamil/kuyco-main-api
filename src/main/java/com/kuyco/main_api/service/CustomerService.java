package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateCustomerDto;
import com.kuyco.main_api.dto.CustomerDto;
import com.kuyco.main_api.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    void create(CreateCustomerDto createDto);
    void deleteById(Long id);
    Page<CustomerDto> getAll(Pageable pageable);
    ItemDto getById(Long id);
    void updateById(Long id, CreateCustomerDto updateDto);
}
