package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Apartment;
import com.javaacademy.flat_rent.exception.NotFoundException;
import com.javaacademy.flat_rent.repository.ApartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = ApartmentMapper.class)
public abstract class AdvertMapper {
    private static final String APARTMENT_NOT_FOUND = "Не найдены апартаменты с таким id: %s.";

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingList", ignore = true)
    @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "getApartment")
    public abstract Advert toEntityWithRelation(AdvertDtoRq dto);

    public abstract AdvertDtoRs toDto(Advert entity);

    public abstract List<AdvertDtoRs> toDtos(List<Advert> entity);

    @Named("getApartment")
    protected Apartment getApartmentById(Integer apartmentId) {
        return apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new NotFoundException(
                        APARTMENT_NOT_FOUND.formatted(apartmentId)));
    }

    public abstract PageDto<AdvertDtoRs> toPage(Page<Advert> page);
}
