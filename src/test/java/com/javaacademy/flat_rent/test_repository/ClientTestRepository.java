package com.javaacademy.flat_rent.test_repository;

import com.javaacademy.flat_rent.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientTestRepository extends JpaRepository<Client, Integer> {

    @Query(value = "select c.* from client c order by id limit 1", nativeQuery = true)
    Optional<Client> findFirstClient();
}
