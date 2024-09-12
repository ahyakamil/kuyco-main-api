package com.kuyco.main_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoryFilterDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long itemCategoryId;
}
