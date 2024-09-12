package com.kuyco.main_api.service;

import com.kuyco.main_api.dto.CreateItemCategoryDto;
import com.kuyco.main_api.dto.ItemCategoryDto;
import com.kuyco.main_api.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemCategoryService {
    void create(CreateItemCategoryDto createItemCategoryDto);
    Page<ItemCategoryDto> getAll(Pageable pageable);
    ItemCategoryDto getById(Long id);
}
