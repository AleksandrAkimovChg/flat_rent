package com.javaacademy.flat_rent.service.apartment;

import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.entity.Apartment;
import com.javaacademy.flat_rent.mapper.ApartmentMapper;
import com.javaacademy.flat_rent.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    @Override
    public ApartmentDto create(ApartmentDto dto) {
        Apartment apartment = apartmentMapper.toEntity(dto);
        Apartment saved = apartmentRepository.save(apartment);
        return apartmentMapper.toDto(saved);
    }
}
