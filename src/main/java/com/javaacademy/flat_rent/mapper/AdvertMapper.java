package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Apartment;
import com.javaacademy.flat_rent.service.apartment.ApartmentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = ApartmentMapper.class)
public abstract class AdvertMapper {

    @Autowired
    private ApartmentService apartmentService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingList", ignore = true)
    @Mapping(target = "apartment", source = "apartment", qualifiedByName = "getApartment")
    public abstract Advert toEntityWithRelation(AdvertDtoRq dto);

    public abstract AdvertDtoRs toDto(Advert entity);

    @Named("getApartment")
    protected Apartment getApartment(Integer apartmentId) {
        return apartmentService.findById(apartmentId);
    }
}
