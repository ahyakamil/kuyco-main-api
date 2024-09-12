package com.kuyco.main_api.controller;

import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.dto.CreateItemCategoryDto;
import com.kuyco.main_api.dto.ItemCategoryDto;
import com.kuyco.main_api.dto.RegisterDto;
import com.kuyco.main_api.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> register(@RequestBody CreateItemCategoryDto createItemCategoryDto) {
        itemCategoryService.create(createItemCategoryDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ItemCategoryDto>>> getAll(Pageable pageable) {
        Page<ItemCategoryDto> itemCategories = itemCategoryService.getAll(pageable);
        return new ResponseWrapper<Page<ItemCategoryDto>>().buildResponseOk(itemCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ItemCategoryDto>> getById(@PathVariable("id") Long id) {
        ItemCategoryDto itemCategory = itemCategoryService.getById(id);
        return new ResponseWrapper<ItemCategoryDto>().buildResponseOk(itemCategory);
    }
}
