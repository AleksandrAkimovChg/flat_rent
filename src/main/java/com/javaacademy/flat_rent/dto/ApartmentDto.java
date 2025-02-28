package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaacademy.flat_rent.entity.ApartmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {

    private Integer id;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String house;

    @NonNull
    @JsonProperty("apartment_type")
    private ApartmentType apartmentType;
}
