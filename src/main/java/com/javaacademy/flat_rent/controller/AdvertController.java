package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.service.advert.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/advert")
@RequiredArgsConstructor
public class AdvertController {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private final AdvertService advertService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertDtoRs create(@RequestBody AdvertDtoRq dto) {
        return advertService.create(dto);
    }

    @GetMapping
    public PageImpl<AdvertDtoRs> getAdverts(
            @RequestParam String city,
            @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null) {
            return advertService.findByCity(city, DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        } else if (pageNumber < 1) {
            throw new PageNumberLessZeroException("Количество страниц должно быть больше 0");
        }
        return  advertService.findByCity(city, pageNumber, DEFAULT_PAGE_SIZE);
    }
}
