package com.kuyco.main_api.service.impl;

import com.kuyco.main_api.config.Validation;
import com.kuyco.main_api.constant.ErrorMessage;
import com.kuyco.main_api.domain.Item;
import com.kuyco.main_api.domain.ItemCategory;
import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.ItemCategoryDto;
import com.kuyco.main_api.dto.ItemDto;
import com.kuyco.main_api.exception.NotFoundException;
import com.kuyco.main_api.repository.ItemCategoryRepository;
import com.kuyco.main_api.repository.ItemRepository;
import com.kuyco.main_api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private final Validation validation;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;


    public ItemServiceImpl(Validation validation) {
        this.validation = validation;
    }

    @Override
    public void create(CreateItemDto createDto) {
        validation.validate(createDto);
        ItemCategory itemCategory = itemCategoryRepository.findById(createDto.getCategoryId()).orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_CATEGORY_NOT_FOUND));

        Item item = new Item();
        item.setName(createDto.getName());
        item.setPrice(createDto.getPrice());
        item.setCategory(itemCategory);
        itemRepository.save(item);
    }

    @Override
    public Page<ItemDto> getAll(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return items.map(item -> {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(item.getId());
            itemDto.setName(item.getName());
            itemDto.setPrice(item.getPrice());

            ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
            itemCategoryDto.setId(item.getCategory().getId());
            itemCategoryDto.setName(item.getCategory().getName());

            itemDto.setCategory(itemCategoryDto);
            return itemDto;
        });
    }

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NotFoundException::new);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());

        ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
        itemCategoryDto.setId(item.getCategory().getId());
        itemCategoryDto.setName(item.getCategory().getName());
        itemDto.setCategory(itemCategoryDto);
        return itemDto;
    }
}
