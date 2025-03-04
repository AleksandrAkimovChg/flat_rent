package com.javaacademy.flat_rent.repository;

import com.javaacademy.flat_rent.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            select
                count(*) = 0
            from
                Booking as b
            where
                (b.dateStart between :dateStart and :dateEnd)
            or
                (b.dateEnd between :dateStart and :dateEnd)
            or
                (:dateStart < b.dateStart and b.dateEnd < :dateEnd)
            """)
    boolean checkIsApartmentAvailable(LocalDate dateStart, LocalDate dateEnd);

    void deleteByClientId(Integer clientId);

    Page<Booking> findByClientEmailIgnoreCase(String email, Pageable pageable);
}
