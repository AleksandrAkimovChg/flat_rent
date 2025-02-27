package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Booking;
import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.service.advert.AdvertService;
import com.javaacademy.flat_rent.service.client.ClientService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class}
)
public abstract class BookingMapper {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdvertService advertService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "client", source = "clientId",  qualifiedByName = "getClient")
    @Mapping(target = "advert", source = "advertId",  qualifiedByName = "getAdvert")
    public abstract Booking toEntityWithRelation(BookingDtoRq dto);

    public abstract BookingDtoRs toDto(Booking entity);

    @Named("getClient")
    protected Client getClient(Integer clientId) {
        return clientService.findById(clientId);
    }

    @Named("getAdvert")
    protected Advert getAdvert(Integer advertId) {
        return advertService.findById(advertId);
    }
}
