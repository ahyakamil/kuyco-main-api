package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.domain.ItemCategory;
import com.kuyco.main_api.dto.CreateItemCategoryDto;
import com.kuyco.main_api.dto.ItemCategoryDto;
import com.kuyco.main_api.exception.NotFoundException;
import com.kuyco.main_api.repository.ItemCategoryRepository;
import com.kuyco.main_api.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private Validation validation;

    @Override
    public void create(CreateItemCategoryDto createItemCategoryDto) {
        validation.validate(createItemCategoryDto);

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setName(createItemCategoryDto.getName());
        itemCategoryRepository.save(itemCategory);
    }

    @Override
    public Page<ItemCategoryDto> getAll(Pageable pageable) {
        Page<ItemCategory> itemCategoryPage = itemCategoryRepository.findAll(pageable);
        return itemCategoryPage.map(itemCategory -> {
            ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
            itemCategoryDto.setId(itemCategory.getId());
            itemCategoryDto.setName(itemCategory.getName());
            return itemCategoryDto;
        });
    }

    @Override
    public ItemCategoryDto getById(Long id) {
        ItemCategory itemCategory = itemCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
        ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
        itemCategoryDto.setId(itemCategory.getId());
        itemCategoryDto.setName(itemCategory.getName());
        return itemCategoryDto;
    }
}
