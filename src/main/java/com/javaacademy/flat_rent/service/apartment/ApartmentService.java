package com.javaacademy.flat_rent.service.apartment;

import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.entity.Apartment;

public interface ApartmentService {

    ApartmentDto createApartment(ApartmentDto dto);

    Apartment findById(Integer apartmentId);
}
