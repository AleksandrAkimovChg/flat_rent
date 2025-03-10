package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class BookingDtoRq {
    @Schema(description = "id объявления", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Информация о клиенте")
    @NonNull
    private ClientDto client;

    @Schema(description = "id объявления", example = "5")
    @JsonProperty("advert_id")
    @NonNull
    private Integer advertId;

    @Schema(description = "Информация о клиенте", example = "2025-10-01")
    @JsonProperty("date_start")
    @NonNull
    private LocalDate dateStart;

    @Schema(description = "Информация о клиенте", example = "2025-10-10")
    @JsonProperty("date_finish")
    @NonNull
    private LocalDate dateEnd;

    @JsonCreator
    public BookingDtoRq(@NonNull ClientDto client,
                        @NonNull Integer advertId,
                        @NonNull LocalDate dateStart,
                        @NonNull LocalDate dateEnd) {
        this.client = client;
        this.advertId = advertId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
