package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import org.springframework.data.domain.PageImpl;

public interface AdvertService {

    AdvertDtoRs create(AdvertDtoRq dto);

    PageImpl<AdvertDtoRs> findByCity(String city, int pageNumber, int pageSize);
}
