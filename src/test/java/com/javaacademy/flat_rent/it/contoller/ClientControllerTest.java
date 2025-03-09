package com.javaacademy.flat_rent.it.contoller;

import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.repository.BookingRepository;
import com.javaacademy.flat_rent.repository.ClientRepository;
import com.javaacademy.flat_rent.test_repository.ClientTestRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientControllerTest {

    private static final String BASE_PATH = "/api/v1/client";

    @Autowired
    private final SessionFactory sessionFactory;
    @Autowired
    private final EntityManager entityManager;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ClientTestRepository clientTestRepository;
    @Autowired
    private ClientRepository clientRepository;

    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBasePath(BASE_PATH)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Успешное удаление клиента с его бронированиями")
    @Sql(value = {"classpath:sql/create-many-bookings-test.sql"})
    public void successDeleteClientWithBookings() {
        Client firstAdvert = clientTestRepository.findFirstClient().orElseThrow();

        sessionFactory.createQuery();

        RestAssured.given(requestSpec)
                .delete(firstAdvert.getId().toString())
                .then()
                .spec(responseSpec)
                .statusCode(HttpStatus.OK.value());

        assertFalse(clientRepository.existsById(firstAdvert.getId()));
    }
}
