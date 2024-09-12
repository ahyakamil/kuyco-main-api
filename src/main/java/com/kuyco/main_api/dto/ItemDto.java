package com.kuyco.main_api.dto;

import com.kuyco.main_api.domain.ItemCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private Double price;
    private ItemCategoryDto category;
}
