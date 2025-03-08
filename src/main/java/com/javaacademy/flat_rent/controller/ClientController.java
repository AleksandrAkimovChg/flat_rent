package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Client controller")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    public static final String TRY_IT_LATER = "Повторите попытку позднее.";
    private final ClientService clientService;

    @Operation(summary = "Удаление записи о клиенте",
            description = "Можно удалить клиента. Вместе с ним удаляются записи о всех бронированиях клиента")
    @ApiResponse(
            responseCode = "200",
            description = "Успешное удаление записи о клиенте",
            content = {@Content(schema = @Schema())}
    )
    @ApiResponse(
            responseCode = "409",
            description = "Не успешное удаление записи о клиенте. Попробуйте позже",
            content = {
                    @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "id клиента, которого нужно удалить", example = "5")
            @PathVariable Integer id) {
        if (clientService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(TRY_IT_LATER);
    }
}
