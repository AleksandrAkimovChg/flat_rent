package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.exception.SubjectNotFoundException;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.mapper.ClientMapper;
import com.javaacademy.flat_rent.repository.ClientRepository;
import com.javaacademy.flat_rent.service.booking.BookingService;
import com.javaacademy.flat_rent.service.client.ClientService;
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
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int PAGE_SIZE_TWENTY = 20;
    public static final String CLIENT_NOT_FOUND = "Нет клиента с такми id: %s";

    private final BookingService bookingService;
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoRs create(@RequestBody BookingDtoRq bookingDtoRq) {
        if (bookingDtoRq.getClient().getId() == null) {
            ClientDto clientDto = clientMapper.toDto(bookingDtoRq);
            clientService.create(clientDto);
        } else {
            clientRepository.findById(bookingDtoRq.getClient().getId())
                    .orElseThrow(() -> new SubjectNotFoundException(
                            CLIENT_NOT_FOUND.formatted((bookingDtoRq.getClient().getId()))));
        }
        return bookingService.create(bookingDtoRq);
    }

    @GetMapping
    public PageImpl<BookingDtoRs> getBookingsByEmail(
            @RequestParam String email,
            @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null) {
            return bookingService.findByEmail(email, DEFAULT_PAGE_NUMBER, PAGE_SIZE_TWENTY);
        } else if (pageNumber < 1) {
            throw new PageNumberLessZeroException("Количество страниц должно быть больше 0");
        }
        return bookingService.findByEmail(email, pageNumber, PAGE_SIZE_TWENTY);
    }
}
