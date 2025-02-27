package com.javaacademy.flat_rent.service.client;

import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.mapper.ClientMapper;
import com.javaacademy.flat_rent.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto create(ClientDto dto) {
        Client client = clientMapper.toEntity(dto);
        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }
}
