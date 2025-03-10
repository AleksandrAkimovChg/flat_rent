package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Apartment controller", description = "API для внесения в бд записи об апартаментах")
@RestController
@RequestMapping("/api/v1/apartment")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartService;

    @Operation(summary = "Создание записи об апартаменте",
            description = "Можно внести данные об апартаменте")
    @ApiResponse(
            responseCode = "201",
            description = "Успешное внесение записи об апартаменте",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ApartmentDto.class)))
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto create(@RequestBody ApartmentDto dto) {
        return apartService.create(dto);
    }
}
