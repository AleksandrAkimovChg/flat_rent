package com.javaacademy.flat_rent.test_repository;

import com.javaacademy.flat_rent.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdvertTestRepository extends JpaRepository<Advert, Integer> {

    @Query(value = "select a.* from advert as a order by id limit 1", nativeQuery = true)
    Optional<Advert> findFirstAdvert();
}
