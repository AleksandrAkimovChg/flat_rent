package com.javaacademy.flat_rent.service.client;

import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.entity.Client;
import com.javaacademy.flat_rent.exception.EntityNotFoundException;
import com.javaacademy.flat_rent.mapper.ClientMapper;
import com.javaacademy.flat_rent.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    public static final String CLIENT_NOT_FOUND = "Не найден клиент с таким id: %s";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto createAClient(ClientDto dto) {
        Client client = clientMapper.toEntity(dto);
        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }

    @Override
    public Client findById(Integer clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CLIENT_NOT_FOUND.formatted(clientId)));
    }
}
