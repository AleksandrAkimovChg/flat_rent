package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.ApartmentDto;
import com.javaacademy.flat_rent.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adverts", ignore = true)
    @Mapping(target = "houseNumber", source = "house")
    Apartment toEntity(ApartmentDto dto);

    @Mapping(target = "house", source = "houseNumber")
    ApartmentDto toDto(Apartment entity);
}
