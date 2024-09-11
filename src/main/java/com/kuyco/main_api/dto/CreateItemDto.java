package com.kuyco.main_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemDto {
    private String name;
    private Double price;
    private Long categoryId;
}
