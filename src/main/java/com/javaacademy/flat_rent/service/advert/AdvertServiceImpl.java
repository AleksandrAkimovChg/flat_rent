package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.exception.EntityNotFoundException;
import com.javaacademy.flat_rent.mapper.AdvertMapper;
import com.javaacademy.flat_rent.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    public static final String ADVERT_NOT_FOUND = "Не найдено объявление с таким id: %s";

    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;

    @Override
    public AdvertDtoRs createAdvice(AdvertDtoRq dto) {
        Advert advert = advertMapper.toEntityWithRelation(dto);
        Advert saved = advertRepository.save(advert);
        return advertMapper.toDto(saved);
    }

    @Override
    public Advert findById(Integer advertId) {
        return advertRepository.findById(advertId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ADVERT_NOT_FOUND.formatted(advertId)));
    }
}
