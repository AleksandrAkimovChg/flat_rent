package com.javaacademy.flat_rent.service.booking;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String NOT_ACTIVE_ADVERT = "Выбрано неактивное объявление №: %s";
    private static final String BOOKING_START_DAY_LATER_DAY_END = "Дата начала брони позже даты окончания";
    public static final String APARTMENT_IS_NOT_AVAILABLE = "Апартаменты на выбранные дни заняты";

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CalcBooking calcBooking;

    @Override
    public BookingDtoRs create(BookingDtoRq dto) {
        Booking booking = bookingMapper.toEntityWithRelation(dto);
        checkIsAdvertActive(booking.getAdvert());
        checkIsApartmentAvailable(booking);
        BigDecimal bookingPrice = getBookingTotalPrice(booking);
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
        boolean isAvailable = bookingRepository.checkIsApartmentAvailable(
                booking.getDateStart(),
                booking.getDateEnd());
        if (!isAvailable) {
            throw new BookingIsNotAvailableException(APARTMENT_IS_NOT_AVAILABLE);
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

    @Override
    public PageImpl<BookingDtoRs> findByEmail(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Booking> bookingPage = bookingRepository.findByClientEmail(name, pageRequest);
        List<Booking> bookingList = bookingPage.getContent();
        List<BookingDtoRs> content = bookingMapper.toDtos(bookingList);
        return new PageImpl<>(content, pageRequest, bookingPage.getTotalElements());
    }
}
