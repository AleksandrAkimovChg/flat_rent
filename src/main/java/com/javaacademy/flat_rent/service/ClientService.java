package com.javaacademy.flat_rent.service;

import com.javaacademy.flat_rent.dto.ClientDto;

public interface ClientService {

    ClientDto create(ClientDto dto);

    boolean delete(Integer id);
}
