package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;

public interface AdvertService {

    AdvertDtoRs create(AdvertDtoRq dto);

    PageDto<AdvertDtoRs> findByCity(String city, int pageNumber, int pageSize);
}
