package com.javaacademy.flat_rent.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    @ArraySchema(schema = @Schema(description = "Контент (содержание) страницы",
            type = "object",
            oneOf = {AdvertDtoRs.class, BookingDtoRs.class}
    ))
    private List<T> content;

    @Schema(description = "Номер страницы выдачи", example = "0")
    private Integer number;

    @Schema(description = "Размер контента на странице выдачи", example = "10")
    private Integer size;

    @Schema(description = "Количество элементов контента на странице выдачи", example = "10")
    private Integer numberOfElements;

    @Schema(description = "Общее количество страниц", example = "5")
    private Integer totalPages;

    @Schema(description = "Общее количество всех элементов", example = "55")
    private Integer totalElements;
}
