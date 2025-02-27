package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class AdvertDtoRq {

    private Integer id;

    @NonNull
    private BigDecimal price;

    @NonNull
    @JsonProperty("is_active")
    private Boolean isActive;

    @NonNull
    @JsonProperty("apartment_id")
    private Integer apartmentId;

    @NonNull
    private String description;
}
