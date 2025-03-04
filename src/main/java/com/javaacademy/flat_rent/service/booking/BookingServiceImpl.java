package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Booking;
import com.javaacademy.flat_rent.exception.AdvertIsNotActiveException;
import com.javaacademy.flat_rent.exception.BookingIsNotAvailableException;
import com.javaacademy.flat_rent.exception.BookingStartDayLaterDayEndException;
import com.javaacademy.flat_rent.mapper.BookingMapper;
import com.javaacademy.flat_rent.repository.BookingRepository;
import com.javaacademy.flat_rent.service.calc.CalcBooking;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String NOT_ACTIVE_ADVERT = "Выбрано неактивное объявление №: %s.";
    private static final String BOOKING_START_DAY_LATER_DAY_END = "Дата начала брони %s позже даты окончания %s.";
    public static final String APARTMENT_IS_NOT_AVAILABLE = "На выбранные дни (c %s по %s) апартаменты заняты.";

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CalcBooking calcBooking;

    @Override
    @Transactional
    public BookingDtoRs create(BookingDtoRq dto) {
        Booking booking = bookingMapper.toEntityWithRelation(dto);
        checkIsAdvertActive(booking.getAdvert());
        checkIsApartmentAvailable(booking);
        BigDecimal bookingPrice = calcBooking.calcBookingTotalPrice(
                booking.getDateStart(),
                booking.getDateEnd(),
                booking.getAdvert().getPrice());
        booking.setPrice(bookingPrice);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }

    private void checkIsAdvertActive(Advert advert) {
        if (!advert.getIsActive()) {
            throw new AdvertIsNotActiveException(
                    NOT_ACTIVE_ADVERT.formatted(advert.getId()));
        }
    }

    private void checkIsApartmentAvailable(Booking booking) {
        if (booking.getDateStart().isAfter(booking.getDateEnd())) {
            throw new BookingStartDayLaterDayEndException(BOOKING_START_DAY_LATER_DAY_END.formatted(
                    booking.getDateStart(),
                    booking.getDateEnd()
            ));
        }
        boolean isAvailable = bookingRepository.checkIsApartmentAvailable(
                booking.getDateStart(),
                booking.getDateEnd());
        if (!isAvailable) {
            throw new BookingIsNotAvailableException(APARTMENT_IS_NOT_AVAILABLE.formatted(
                    booking.getDateStart(),
                    booking.getDateEnd()
            ));
        }
    }

    @Override
    public PageDto<BookingDtoRs> findByEmail(String email, int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Booking> bookingPage = bookingRepository.findByClientEmailIgnoreCase(email, pageRequest);
        return bookingMapper.toPageBookingDtoRs(bookingPage);
    }
}
