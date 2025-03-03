package com.javaacademy.flat_rent.mapper;

import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.BookingDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageMapper {

    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    PageDto<AdvertDtoRs> toAdvertDtoRs(PageImpl<AdvertDtoRs> pageImpl);

    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    PageDto<BookingDtoRs> toBookingDtoRs(PageImpl<BookingDtoRs> pageImpl);
}
