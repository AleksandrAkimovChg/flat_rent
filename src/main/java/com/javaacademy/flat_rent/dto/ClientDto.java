package com.javaacademy.flat_rent.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public ClientDto(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }
}
