package com.javaacademy.flat_rent.service.calc;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalcBooking {

    BigDecimal calcBookingAmount(LocalDate dateStart, LocalDate dateEnd, BigDecimal price);
}
