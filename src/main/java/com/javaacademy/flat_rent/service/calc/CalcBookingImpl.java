package com.javaacademy.flat_rent.service.calc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalcBookingImpl implements CalcBooking {

    @Override
    public BigDecimal calcBookingAmount(LocalDate dateStart, LocalDate dateEnd, BigDecimal price) {
        long days = ChronoUnit.DAYS.between(dateStart, dateEnd);
        return price.multiply(BigDecimal.valueOf(days));
    }
}
