package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;

public interface AdvertService {

    AdvertDtoRs create(AdvertDtoRq dto);
}
