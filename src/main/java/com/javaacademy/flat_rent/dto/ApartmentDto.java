package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaacademy.flat_rent.entity.ApartmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {
    @Schema(description = "id ", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Город", example = "Москва")
    @NonNull
    private String city;

    @Schema(description = "Улица", example = "Центральная")
    @NonNull
    private String street;

    @Schema(description = "Дом", example = "дом 10 корп. 1")
    @NonNull
    private String house;

    @Schema(description = "Тип апартаментов", example = "TWO_BEDROOM")
    @JsonProperty("apartment_type")
    @NonNull
    private ApartmentType apartmentType;

    @JsonCreator
    public ApartmentDto(@NonNull String city,
                        @NonNull String street,
                        @NonNull String house,
                        @NonNull ApartmentType apartmentType) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartmentType = apartmentType;
    }
}
