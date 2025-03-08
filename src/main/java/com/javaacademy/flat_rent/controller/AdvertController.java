package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.service.advert.AdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Advert controller")
@RestController
@RequestMapping("/api/v1/advert")
@RequiredArgsConstructor
public class AdvertController {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String PAGE_NUMBER_LESS_ZERO = "Количество страниц должно быть больше 0.";

    private final AdvertService advertService;

    @Operation(summary = "Создание записи о объявлении",
            description = "Можно внести запись об объявлении")
    @ApiResponse(
            responseCode = "201",
            description = "Успешное внесение информации о клиенте",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdvertDtoRs.class))
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertDtoRs create(@RequestBody AdvertDtoRq dto) {
        return advertService.create(dto);
    }

    @Operation(summary = "Поиск объявления по названию города",
            description = "Можно найти объявление по названию города")
    @ApiResponse(
            responseCode = "200",
            description = "Успешный ответ сервера",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PageDto.class))
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Не успешно. Запрос содержит ошибки",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Не успешно. Не найдены апартаменты c указанным id или номер страницы меньше 1",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping
    public PageDto<AdvertDtoRs> getByCity(
            @Parameter(description = "Название города по которому нужно построить поисковую выдачу", example = "5")
            @RequestParam String city,
            @Parameter(description = "Номер страницы для пагинации начиная с 1", example = "1")
            @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null) {
            return advertService.findByCity(city, DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        } else if (pageNumber < 1) {
            throw new PageNumberLessZeroException(PAGE_NUMBER_LESS_ZERO);
        }
        return advertService.findByCity(city, pageNumber, DEFAULT_PAGE_SIZE);
    }
}
