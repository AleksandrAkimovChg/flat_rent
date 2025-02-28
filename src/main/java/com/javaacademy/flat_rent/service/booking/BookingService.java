package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;

public interface BookingService {

    BookingDtoRs create(BookingDtoRq dto);
}
