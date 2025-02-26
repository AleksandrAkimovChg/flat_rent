package com.javaacademy.flat_rent.service.client;

import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.entity.Client;

public interface ClientService {

    ClientDto createAClient(ClientDto dto);

    Client findById(Integer clientId);
}
