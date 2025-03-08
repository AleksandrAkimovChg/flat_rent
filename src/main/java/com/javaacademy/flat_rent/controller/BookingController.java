package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@Tag(name = "Booking controller")
@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int PAGE_SIZE_TWENTY = 20;
    public static final String NUMBER_OF_PAGE_ZERO_OR_LESS = "Количество страниц должно быть больше 0";
    private final BookingService bookingService;

    @Operation(summary = "Создание записи о бронировании",
            description = "Можно внести запись о бронировании")
    @ApiResponse(
            responseCode = "201",
            description = "Успешное внесение записи о бронировании.",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BookingDtoRs.class)))
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoRs create(@RequestBody BookingDtoRq bookingDtoRq) {
        return bookingService.create(bookingDtoRq);
    }

    @Operation(summary = "Удаление записи о клиенте",
            description = "Можно удалить клиента. Вместе с ним удаляются записи о всех бронированиях клиента")
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение выборки объявлений по названию города",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PageDto.class))
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Не успешно. Запрос содержит ошибки "
                    + "или дата начала бронирования позже дня окончания "
                    + "или объявление а не активном статусе"
                    + "или не найден клиент с указанным id",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Не успешно. Не найдены клиент или объявление c указанным id",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @ApiResponse(
            responseCode = "409",
            description = "Не успешно. На данные дни есть пересечение с существющим бронированем.",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping
    public PageDto<BookingDtoRs> getByEmail(
            @Parameter(description = "Название города по которому нужно сделать выборку", example = "Москва")
            @RequestParam String email,
            @Parameter(description = "Номер страницы для пагинации начиная с 1", example = "1")
            @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null) {
            return bookingService.findByEmail(email, DEFAULT_PAGE_NUMBER, PAGE_SIZE_TWENTY);
        } else if (pageNumber < 1) {
            throw new PageNumberLessZeroException(NUMBER_OF_PAGE_ZERO_OR_LESS);
        }
        return bookingService.findByEmail(email, pageNumber, PAGE_SIZE_TWENTY);
    }
}
