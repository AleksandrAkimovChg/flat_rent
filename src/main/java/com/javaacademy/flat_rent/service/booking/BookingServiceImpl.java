package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.entity.Booking;
import com.javaacademy.flat_rent.exception.AdvertIsNotActiveException;
import com.javaacademy.flat_rent.mapper.BookingMapper;
import com.javaacademy.flat_rent.repository.BookingRepository;
import com.javaacademy.flat_rent.service.advert.AdvertService;
import com.javaacademy.flat_rent.service.calc.CalcBooking;
import com.javaacademy.flat_rent.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    public static final String NOT_ACTIVE_ADVERT = "Выбрано неактивное объявление №: %s";
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CalcBooking calcBooking;

    @Override
    public BookingDtoRs createBooking(BookingDtoRq dto) {
        Booking booking = bookingMapper.toEntityWithRelation(dto);
        checkIsAdvertActive(booking);
        BigDecimal bookingPrice = getBookingAmount(booking);
        booking.setPrice(bookingPrice);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }

    private void checkIsAdvertActive(Booking booking) {
        if (!booking.getAdvert().getIsActive()) {
            throw new AdvertIsNotActiveException(
                    NOT_ACTIVE_ADVERT.formatted(booking.getAdvert().getId()));
        }
    }

    private BigDecimal getBookingAmount(Booking booking) {
        return calcBooking.calcBookingAmount(
                booking.getDateStart(),
                booking.getDateEnd(),
                booking.getAdvert().getPrice());
    }
}
