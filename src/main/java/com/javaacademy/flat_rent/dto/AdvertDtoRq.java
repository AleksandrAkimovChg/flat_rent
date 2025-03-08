package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class AdvertDtoRq {
    @Schema(description = "id объявления", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Стоимость посуточного бронирования", example = "500.00")
    @NonNull
    private BigDecimal price;

    @Schema(description = "Признак активности объявления", example = "true")
    @JsonProperty("is_active")
    @NonNull
    private Boolean isActive;

    @Schema(description = "id апартаментов", example = "5")
    @JsonProperty("apartment_id")
    @NonNull
    private Integer apartmentId;


    @Schema(description = "Описание объекта объявления", example = "Самые лучшие апартаменты")
    @NonNull
    private String description;

    @JsonCreator
    public AdvertDtoRq(@NonNull BigDecimal price,
                       @NonNull Boolean isActive,
                       @NonNull Integer apartmentId,
                       @NonNull String description) {
        this.price = price;
        this.isActive = isActive;
        this.apartmentId = apartmentId;
        this.description = description;
    }
}
