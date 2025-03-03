package com.javaacademy.flat_rent.service.client;

import com.javaacademy.flat_rent.dto.ClientDto;

public interface ClientService {

    ClientDto create(ClientDto dto);

    boolean delete(Integer id);
}
