package com.javaacademy.flat_rent;

import com.javaacademy.flat_rent.service.advert.AdvertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlatRentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlatRentApplication.class, args);
        AdvertService advertService = context.getBean(AdvertService.class);
    }

}
