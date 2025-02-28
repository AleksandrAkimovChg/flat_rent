package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.entity.Booking;
import com.javaacademy.flat_rent.exception.AdvertIsNotActiveException;
import com.javaacademy.flat_rent.exception.BookingStartDayLaterDayEndException;
import com.javaacademy.flat_rent.mapper.BookingMapper;
import com.javaacademy.flat_rent.repository.BookingRepository;
import com.javaacademy.flat_rent.service.calc.CalcBooking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String NOT_ACTIVE_ADVERT = "Выбрано неактивное объявление №: %s";
    private static final String BOOKING_START_DAY_LATER_DAY_END = "Дата начала брони позже даты окончания";

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CalcBooking calcBooking;

    @Override
    public BookingDtoRs create(BookingDtoRq dto) {
        Booking booking = bookingMapper.toEntityWithRelation(dto);
        checkIsAdvertActive(booking);
        BigDecimal bookingPrice = getBookingTotalPrice(booking);
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

    private BigDecimal getBookingTotalPrice(Booking booking) {
        if (booking.getDateStart().isAfter(booking.getDateEnd())) {
            throw new BookingStartDayLaterDayEndException(BOOKING_START_DAY_LATER_DAY_END);
        }
        return calcBooking.calcBookingTotalPrice(
                booking.getDateStart(),
                booking.getDateEnd(),
                booking.getAdvert().getPrice());
    }
}
