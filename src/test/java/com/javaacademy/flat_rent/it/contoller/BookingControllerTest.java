package com.javaacademy.flat_rent.it.contoller;

import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.mapper.AdvertMapper;
import com.javaacademy.flat_rent.mapper.ClientMapper;
import com.javaacademy.flat_rent.repository.AdvertRepository;
import com.javaacademy.flat_rent.service.client.ClientService;
import com.javaacademy.flat_rent.test_repository.AdvertTestRepository;
import com.javaacademy.flat_rent.test_repository.ClientTestRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.javaacademy.flat_rent.it.contoller.AdvertControllerTest.DEFAULT_PAGE;
import static com.javaacademy.flat_rent.service.booking.BookingServiceImpl.APARTMENT_IS_NOT_AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookingControllerTest {

    private static final String BASE_PATH = "/api/v1/booking";
    public static final String QUERY_PARAM = "email";
    public static final String QUERY_PARAM_VALUE = "y228@ya.ru";
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_NUMBER_OF_ELEMENTS_ON_PAGE = 20;
    public static final int COUNT_TOTAL_PAGE_FOR_TEST = 1;
    public static final int COUNT_TOTAL_ELEMENTS_FOR_TEST = 20;

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private AdvertTestRepository advertTestRepository;
    @Autowired
    private ClientTestRepository clientTestRepository;


    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBasePath(BASE_PATH)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Успешное бронирование с несуществующим клиентом")
    @Sql(value = {"classpath:sql/clear-booking.sql"})
    public void successCreateBookingClientNotExist() {
        String timestemp = " - тестовый прогон от %s".formatted(LocalDateTime.now().toString());
        ClientDto clientDto = new ClientDto(
                null,
                "Имя %s ".formatted(timestemp),
                "email@test.ru %s ".formatted(timestemp)
        );
        Advert expectedAdvert = advertTestRepository.findFirstAdvert().orElseThrow();
        BookingDtoRq request = new BookingDtoRq(
                clientDto,
                expectedAdvert.getId(),
                LocalDate.parse("2025-02-13"),
                LocalDate.parse("2025-02-23")
        );
        BigDecimal multiplied = expectedAdvert.getPrice().multiply(BigDecimal.TEN);
        AdvertDtoRs expectedAdvertDtoRs = advertMapper.toDto(expectedAdvert);

        BookingDtoRs response = RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(BookingDtoRs.class);

        ClientDto client = response.getClient();
        assertEquals(request.getDateStart(), response.getDateStart());
        assertEquals(request.getDateEnd(), response.getDateEnd());
        assertEquals(multiplied, response.getPrice());

        assertEquals(expectedAdvertDtoRs, response.getAdvert());

        assertEquals(clientDto.getName(), client.getName());
        assertEquals(clientDto.getEmail(), client.getEmail());
    }

    @Test
    @DisplayName("Успешное бронирование с существующим клиентом")
    @Sql(value = {"classpath:sql/clear-booking.sql"})
    public void successCreateBookingClientExist() {
        Client expectedClient = clientTestRepository.findFirstClient().orElseThrow();
        Advert expectedAdvert = advertTestRepository.findFirstAdvert().orElseThrow();
        ClientDto expectedClientDto = clientMapper.toDto(expectedClient);
        BookingDtoRq request = new BookingDtoRq(
                expectedClientDto,
                expectedAdvert.getId(),
                LocalDate.parse("2025-02-13"),
                LocalDate.parse("2025-02-23")
        );
        BigDecimal multiplied = expectedAdvert.getPrice().multiply(BigDecimal.TEN);
        AdvertDtoRs expectedAdvertDtoRs = advertMapper.toDto(expectedAdvert);

        BookingDtoRs response = RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(BookingDtoRs.class);
        ClientDto client = response.getClient();

        assertEquals(request.getDateStart(), response.getDateStart());
        assertEquals(request.getDateEnd(), response.getDateEnd());
        assertEquals(multiplied, response.getPrice());

        assertEquals(expectedClientDto, response.getClient());
        assertEquals(expectedAdvertDtoRs, response.getAdvert());
    }

    @Test
    @DisplayName("Неуспешное бронирование с пересечением дат внутри другого бронирования")
    @Sql(value = {"classpath:sql/create-booking-test.sql"})
    public void failureCreateBookingWhenBookingExist() {
        String dateStart = "2025-10-05";
        String dateEnd = "2025-10-06";
        Client expectedClient = clientTestRepository.findFirstClient().orElseThrow();
        Advert expectedAdvert = advertTestRepository.findFirstAdvert().orElseThrow();
        ClientDto expectedClientDto = clientMapper.toDto(expectedClient);
        BookingDtoRq request = new BookingDtoRq(
                expectedClientDto,
                expectedAdvert.getId(),
                LocalDate.parse(dateStart),
                LocalDate.parse(dateEnd)
        );

        RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CONFLICT.value())
                .body(Matchers.equalTo(APARTMENT_IS_NOT_AVAILABLE.formatted(dateStart, dateEnd)));
    }

    @Test
    @DisplayName("Неуспешное бронирование с пересечением даты окончания внутри другого бронирования")
    @Sql(value = {"classpath:sql/create-booking-test.sql"})
    public void failureCreateBookingWhenLastDayBookingExist() {
        String dateStart = "2025-09-29";
        String dateEnd = "2025-10-02";
        Client expectedClient = clientTestRepository.findFirstClient().orElseThrow();
        Advert expectedAdvert = advertTestRepository.findFirstAdvert().orElseThrow();
        ClientDto expectedClientDto = clientMapper.toDto(expectedClient);
        BookingDtoRq request = new BookingDtoRq(
                expectedClientDto,
                expectedAdvert.getId(),
                LocalDate.parse(dateStart),
                LocalDate.parse(dateEnd)
        );

        RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CONFLICT.value())
                .body(Matchers.equalTo(APARTMENT_IS_NOT_AVAILABLE.formatted(dateStart, dateEnd)));
    }

    @Test
    @DisplayName("Неуспешное бронирование с пересечением даты начала внутри другого бронирования")
    @Sql(value = {"classpath:sql/create-booking-test.sql"})
    public void failureCreateBookingWhenFirstDayBookingExist() {
        String dateStart = "2025-10-09";
        String dateEnd = "2025-10-11";
        Client expectedClient = clientTestRepository.findFirstClient().orElseThrow();
        Advert expectedAdvert = advertTestRepository.findFirstAdvert().orElseThrow();
        ClientDto expectedClientDto = clientMapper.toDto(expectedClient);
        BookingDtoRq request = new BookingDtoRq(
                expectedClientDto,
                expectedAdvert.getId(),
                LocalDate.parse(dateStart),
                LocalDate.parse(dateEnd)
        );

        RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CONFLICT.value())
                .body(Matchers.equalTo(APARTMENT_IS_NOT_AVAILABLE.formatted(dateStart, dateEnd)));
    }

    @Test
    @DisplayName("Успешное получение списка бронирований по email клиента")
    @Sql(value = {"classpath:sql/create-many-booking.sql"})
    public void successGetPageWithDtoAndPriceDesc() {
        PageDto<BookingDtoRs> response = RestAssured.given(requestSpec)
                .queryParam(QUERY_PARAM, QUERY_PARAM_VALUE)
                .get()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        assertEquals(DEFAULT_PAGE, response.getNumber());
        assertEquals(DEFAULT_PAGE_SIZE, response.getSize());
        assertEquals(DEFAULT_NUMBER_OF_ELEMENTS_ON_PAGE, response.getNumberOfElements());
        assertEquals(COUNT_TOTAL_PAGE_FOR_TEST, response.getTotalPages());
        assertEquals(COUNT_TOTAL_ELEMENTS_FOR_TEST, response.getTotalElements());

        assertTrue(response.getContent().stream()
                .allMatch(booking -> booking.getClient().getEmail().equals(QUERY_PARAM_VALUE)));
    }
}
