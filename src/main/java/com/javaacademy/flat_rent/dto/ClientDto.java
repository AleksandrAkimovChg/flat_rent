package com.javaacademy.flat_rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String email;
}
