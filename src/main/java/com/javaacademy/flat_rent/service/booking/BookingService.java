package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import org.springframework.data.domain.PageImpl;

public interface BookingService {

    BookingDtoRs create(BookingDtoRq dto);

    PageImpl<BookingDtoRs> findByEmail(String email, int pageNumber, int pageSize);
}
