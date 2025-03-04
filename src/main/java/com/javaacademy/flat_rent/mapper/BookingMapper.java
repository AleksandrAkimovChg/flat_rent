package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.entity.Booking;
import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.exception.NotFoundException;
import com.javaacademy.flat_rent.repository.AdvertRepository;
import com.javaacademy.flat_rent.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class}
)
public abstract class BookingMapper {
    private static final String CLIENT_NOT_FOUND = "Не найден клиент с таким id: %s";
    private static final String ADVERT_NOT_FOUND = "Не найдено объявление с таким id: %s";

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "client", source = "client.id",  qualifiedByName = "getClient")
    @Mapping(target = "advert", source = "advertId",  qualifiedByName = "getAdvert")
    public abstract Booking toEntityWithRelation(BookingDtoRq dto);

    public abstract BookingDtoRs toDto(Booking entity);

    public abstract List<BookingDtoRs> toDtos(List<Booking> entities);

    @Named("getClient")
    protected Client getClient(Integer clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException(
                        CLIENT_NOT_FOUND.formatted(clientId)));
    }

    @Named("getAdvert")
    protected Advert getAdvert(Integer advertId) {
        return advertRepository.findById(advertId)
                .orElseThrow(() -> new NotFoundException(
                        ADVERT_NOT_FOUND.formatted(advertId)));
    }

    public abstract PageDto<BookingDtoRs> toPage(Page<Booking> page);
}
