package com.javaacademy.flat_rent;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.service.booking.BookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class FlatRentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlatRentApplication.class, args);
        BookingService service = context.getBean(BookingService.class);
        BookingDtoRq dto = new BookingDtoRq(
                new ClientDto(null, "Cektqvfy", "Zregjd123"),
                1,
                LocalDate.parse("2025-02-13"),
                LocalDate.parse("2025-02-13"));
    }

}
