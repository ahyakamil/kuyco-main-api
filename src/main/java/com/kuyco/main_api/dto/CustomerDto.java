package com.kuyco.main_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private Double balance;
}
