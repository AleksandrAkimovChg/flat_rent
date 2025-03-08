package com.javaacademy.flat_rent.it.contoller;

import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.entity.ApartmentType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApartmentControllerTest {

    private static final String BASE_PATH = "/api/v1/apartment";

    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBasePath(BASE_PATH)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Успешное создание апартаментов")
    public void successCreateBooking() {
        ApartmentDto request = new ApartmentDto(
                null,
                "Москва",
                "Центральная",
                "дом 10",
                ApartmentType.TWO_BEDROOM
        );

        ApartmentDto response = RestAssured.given(requestSpec)
                .body(request)
                .post()
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(ApartmentDto.class);

        assertEquals(request.getCity(), response.getCity());
        assertEquals(request.getStreet(), response.getStreet());
        assertEquals(request.getHouse(), response.getHouse());
        assertEquals(request.getApartmentType(), response.getApartmentType());
    }
}
