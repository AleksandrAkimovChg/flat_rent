package com.javaacademy.flat_rent.repository;

import com.javaacademy.flat_rent.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            select count(*) = 0
            from Booking as b
            where (b.dateStart between :dateStart and :dateEnd) or (b.dateStart between :dateStart and :dateEnd)
            """)
    boolean checkIsApartmentAvailable(LocalDate dateStart, LocalDate dateEnd);

    void deleteByClientId(Integer clientId);

    //TODO удалить
    @Modifying
    @Query("""
            delete Booking as b
            where b.client.id = :clientId
            """)
    void deleteInBulkByClientId(Integer clientId);

    Page<Booking> findByClientEmail(String name, PageRequest pageRequest);

    //TODO удалить
    @Query("""
            from Booking as b
            where lower(b.client.email) = lower(:email)
            """)
    Page<Booking> findByClientEmail(String email);
}
