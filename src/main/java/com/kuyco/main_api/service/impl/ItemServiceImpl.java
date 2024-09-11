package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.ItemDto;
import com.kuyco.main_api.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public void create(CreateItemDto createDto) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Page<ItemDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public ItemDto getById(Long id) {
        return null;
    }

    @Override
    public void updateById(Long id, CreateItemDto updateDto) {

    }
}
