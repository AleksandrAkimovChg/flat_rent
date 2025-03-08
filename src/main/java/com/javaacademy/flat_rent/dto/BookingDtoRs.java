package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingDtoRs {
    @Schema(description = "id бронирования", example = "5")
    private Integer id;

    @Schema(description = "Информация о клиенте")
    private ClientDto client;

    @Schema(description = "Информация об объявлении")
    private AdvertDtoRs advert;

    @Schema(description = "Дата начала бронирования")
    @JsonProperty("date_start")
    private LocalDate dateStart;

    @Schema(description = "Дата окончания бронирования")
    @JsonProperty("date_finish")
    private LocalDate dateEnd;

    @Schema(description = "Полная стоимость бронирования")
    @JsonProperty("result_price")
    private BigDecimal price;
}
