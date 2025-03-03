package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    public static final String TRY_IT_LATER = "Повторите попытку позднее.";
    private final ClientService clientService;

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (clientService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(TRY_IT_LATER);
    }
}
