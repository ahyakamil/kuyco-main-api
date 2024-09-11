package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    void create(CreateItemDto createDto);
    void deleteById(Long id);
    Page<ItemDto> getAll(Pageable pageable);
    ItemDto getById(Long id);
    void updateById(Long id, CreateItemDto updateDto);
}
