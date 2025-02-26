package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.entity.Advert;

public interface AdvertService {

    AdvertDtoRs createAdvice(AdvertDtoRq dto);

    Advert findById(Integer advertId);
}
