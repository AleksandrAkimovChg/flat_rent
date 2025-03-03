package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;

public interface BookingService {

    BookingDtoRs create(BookingDtoRq dto);

    PageDto<BookingDtoRs> findByEmail(String email, int pageNumber, int pageSize);
}
