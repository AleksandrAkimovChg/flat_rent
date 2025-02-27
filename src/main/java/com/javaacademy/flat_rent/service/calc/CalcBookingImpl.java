package com.javaacademy.flat_rent.service.calc;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CalcBookingImpl implements CalcBooking {

    @Override
    public BigDecimal calcBookingAmount(LocalDate dateStart, LocalDate dateEnd, BigDecimal price) {
        long days = ChronoUnit.DAYS.between(dateStart, dateEnd);
        return price.multiply(BigDecimal.valueOf(days));
    }
}
