package com.javaacademy.flat_rent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {

        Contact myContact = new Contact()
                .name("Александр Акимов")
                .email("my.email@example.com");

        Info info = new Info()
                .title("API сервиса бронирования")
                .version("1.0")
                .description("Этот API предоставляет эндпоинты для управлением сервсиом посуточного бронирования "
                        + "квартиры")
                .contact(myContact);

        return new OpenAPI()
                .info(info);
    }
}
