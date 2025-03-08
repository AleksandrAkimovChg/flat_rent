package com.javaacademy.flat_rent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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

        List<Tag> tags = new ArrayList<>(List.of(
                new Tag().name("Apartment controller")
                        .description("API для внесения в бд записи об апартаментах."),
                new Tag().name("Advert controller")
                        .description("API для внесения записи об объявлениях о посуточной аренде и поиска объявлений "
                                + "по названию города."),
                new Tag().name("Client controller")
                        .description("API для удаления в бд записи клиенте."),
                new Tag().name("Booking controller")
                        .description("API для внесения в бд записи о бронировании.")
        ));

        return new OpenAPI()
                .info(info)
                .tags(tags);
    }
}
