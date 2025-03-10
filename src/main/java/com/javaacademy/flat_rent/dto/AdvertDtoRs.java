package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdvertDtoRs {
    @Schema(description = "id объявления", example = "5")
    private Integer id;

    @Schema(description = "Стоимость посуточного бронирования", example = "500.00")
    private BigDecimal price;

    @Schema(description = "Признак активности объявления", example = "true")
    @JsonProperty("is_active")
    private Boolean isActive;

    @Schema(description = "Апартаменты и их свойства")
    private ApartmentDto apartment;

    @Schema(description = "Описание объекта объявления", example = "Самые лучшие апартаменты")
    private String description;
}
