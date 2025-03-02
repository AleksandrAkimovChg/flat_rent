package com.javaacademy.flat_rent.service.advert;

import com.javaacademy.flat_rent.dto.AdvertDtoRq;
import com.javaacademy.flat_rent.dto.AdvertDtoRs;
import com.javaacademy.flat_rent.entity.Advert;
import com.javaacademy.flat_rent.mapper.AdvertMapper;
import com.javaacademy.flat_rent.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageImpl<AdvertDtoRs> findByCity(String city, int pageNumber, int pageSize) {
        Sort price = Sort.by(Sort.Direction.DESC, "price");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, price);
        Page<Advert> cityPage = advertRepository.findByCity(city, pageRequest);
        List<Advert> advertList = cityPage.getContent();
        List<AdvertDtoRs> content = advertMapper.toDtos(advertList);
        return new PageImpl<>(content, pageRequest, cityPage.getTotalElements());
    }
}
