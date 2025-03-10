package com.javaacademy.flat_rent.it.contoller;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.ApartmentType;
import com.javaacademy.flat_rent.service.ApartmentService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdvertControllerTest {
    private static final String BASE_PATH = "/api/v1/advert";
    public static final String QUERY_PARAM = "city";
    public static final String QUERY_PARAM_VALUE = "Москва";
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int COUNT_PAGES_FOR_TEST = 2;
    public static final int COUNT_ITEM_FOR_TEST = 11;
    public static final int DEFALT_NUMBER_OF_ELEMENTS = 10;

    @Autowired
    private ApartmentService apartmentService;

    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBasePath(BASE_PATH)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Успешное создание объявления")
    public void successCreateApartment() {
        ApartmentDto apartmentDto = new ApartmentDto(
                null,
                "Москва",
                "Центральная",
                "дом 10",
                ApartmentType.TWO_BEDROOM
        );
        ApartmentDto apartment = apartmentService.create(apartmentDto);
        AdvertDtoRq request = new AdvertDtoRq(
                new BigDecimal("500.00"),
                true,
                apartment.getId(),
                "Тестовые апартаменты %s".formatted(LocalDateTime.now())
        );

        AdvertDtoRs response = RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(AdvertDtoRs.class);

        assertEquals(request.getPrice(), response.getPrice());
        assertEquals(request.getIsActive(), response.getIsActive());
        assertEquals(request.getApartmentId(), response.getApartment().getId());
        assertEquals(request.getDescription(), response.getDescription());
    }

    @Test
    @DisplayName("Успешное получение списка объявлений по городу с сортировкой по убывающей цене")
    @Sql(value = {"classpath:sql/create-adverts-test.sql"})
    public void successGetPageWithDtoAndPriceDesc() {
        PageDto<AdvertDtoRs> response = RestAssured.given(requestSpec)
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
        assertEquals(DEFALT_NUMBER_OF_ELEMENTS, response.getNumberOfElements());
        assertEquals(COUNT_PAGES_FOR_TEST, response.getTotalPages());
        assertEquals(COUNT_ITEM_FOR_TEST, response.getTotalElements());

        List<AdvertDtoRs> content = response.getContent();
        assertEquals(new BigDecimal("1100.00"), content.get(0).getPrice());
        assertEquals(DEFAULT_PAGE_SIZE, content.size());
    }
}
