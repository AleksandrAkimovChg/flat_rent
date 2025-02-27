package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class BookingDtoRq {

    private Integer id;

    @NonNull
    @JsonProperty("client_id")
    private Integer clientId;

    @NonNull
    @JsonProperty("advert_id")
    private Integer advertId;

    @NonNull
    @JsonProperty("date_start")
    private LocalDate dateStart;

    @NonNull
    @JsonProperty("date_finish")
    private LocalDate dateEnd;
}
