package com.javaacademy.flat_rent.repository;

import com.javaacademy.flat_rent.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            select count(*) = 0
            from Booking b
                join b.advert ad
                join ad.apartment ap
            where ap.id = :apartmentId
                and ((:dateStart > b.dateStart and :dateStart < b.dateEnd)
                or (:dateEnd > b.dateStart and :dateEnd < b.dateEnd)
                or (:dateStart < b.dateStart and b.dateEnd < :dateEnd))
            """)
    boolean checkIsApartmentAvailable(Integer apartmentId, LocalDate dateStart, LocalDate dateEnd);

    @Modifying
    @Query("""
            delete Booking as b
            where b.client.id = :clientId
            """)
    void deleteInBulkByClientId(Integer clientId);

    Page<Booking> findByClientEmailIgnoreCase(String email, Pageable pageable);
}
