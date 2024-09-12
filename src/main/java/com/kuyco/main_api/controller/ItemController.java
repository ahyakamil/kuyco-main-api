package com.kuyco.main_api.controller;

import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.dto.CreateItemDto;
import com.kuyco.main_api.dto.ItemDto;
import com.kuyco.main_api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> create(@RequestBody CreateItemDto createItemDto) {
        itemService.create(createItemDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ItemDto>>> getAll(Pageable pageable) {
        Page<ItemDto> items = itemService.getAll(pageable);
        return new ResponseWrapper<Page<ItemDto>>().buildResponseOk(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ItemDto>> getById(@PathVariable("id") Long id) {
        ItemDto item = itemService.getById(id);
        return new ResponseWrapper<ItemDto>().buildResponseOk(item);
    }
}
