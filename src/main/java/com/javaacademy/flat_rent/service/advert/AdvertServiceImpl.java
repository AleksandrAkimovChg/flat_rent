package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.dto.PageDto;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.mapper.AdvertMapper;
import com.javaacademy.flat_rent.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;

    @Override
    public AdvertDtoRs create(AdvertDtoRq dto) {
        Advert advert = advertMapper.toEntityWithRelation(dto);
        Advert saved = advertRepository.save(advert);
        return advertMapper.toDto(saved);
    }

    @Override
    public PageDto<AdvertDtoRs> findByCity(String city, int pageNumber, int pageSize) {
        Sort price = Sort.by(Sort.Direction.DESC, "price");
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, price);
        Page<Advert> cityPage = advertRepository.findByApartmentCityIgnoreCase(city, pageRequest);
        return advertMapper.toPageAdvertDtoRs(cityPage);
    }
}
