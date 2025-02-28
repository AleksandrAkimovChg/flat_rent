package com.javaacademy.flat_rent.service.calc;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalcBooking {

    BigDecimal calcBookingTotalPrice(LocalDate dateStart, LocalDate dateEnd, BigDecimal price);
}
