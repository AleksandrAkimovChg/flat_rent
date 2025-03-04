package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int PAGE_SIZE_TWENTY = 20;
    public static final String NUMBER_OF_PAGE_ZERO_OR_LESS = "Количество страниц должно быть больше 0";
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoRs create(@RequestBody BookingDtoRq bookingDtoRq) {
        return bookingService.create(bookingDtoRq);
    }

    @GetMapping
    public PageDto<BookingDtoRs> getByEmail(
            @RequestParam String email,
            @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null) {
            return bookingService.findByEmail(email, DEFAULT_PAGE_NUMBER, PAGE_SIZE_TWENTY);
        } else if (pageNumber < 1) {
            throw new PageNumberLessZeroException(NUMBER_OF_PAGE_ZERO_OR_LESS);
        }
        return bookingService.findByEmail(email, pageNumber, PAGE_SIZE_TWENTY);
    }
}
