package com.javaacademy.flat_rent.mapper;


import com.javaacademy.flat_rent.dto.BookingDtoRq;
import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingList", ignore = true)
    Client toEntity(ClientDto dto);

    ClientDto toDto(Client entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.client.name")
    @Mapping(target = "email", source = "dto.client.email")
    ClientDto toDto(BookingDtoRq dto);
}
