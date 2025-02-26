package com.javaacademy.flat_rent;

import com.javaacademy.flat_rent.dto.ClientDto;
import com.javaacademy.flat_rent.service.client.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlatRentApplication {
    private static final int THREE = 3;
    private static final int FIVE = 5;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlatRentApplication.class, args);
//        ApartmentService bean = context.getBean(ApartmentService.class);
//        ApartmentDto apartmentDto = new ApartmentDto(
//                null,
//                "Черноголовка",
//                "Центральная улица",
//                "дом 10",
//                "дву комнаты"
//        );
//        ApartmentDto apartmentDto1 = bean.saveApartment(apartmentDto);
//        System.out.println(apartmentDto1);
//
//        AdvertService bean = context.getBean(AdvertService.class);
//        AdvertDtoRq advertDtoRq = new AdvertDtoRq(THREE,
//                new BigDecimal("500"),
//                true,
//                FIVE,
//                "туц туц");
//        AdvertDtoRs advertDtoRs = bean.createAdvice(advertDtoRq);
//        System.out.println(advertDtoRs);

        ClientService clientService = context.getBean(ClientService.class);
        ClientDto clientDto = new ClientDto(null, "Татьяна", "test24032024_2@test.ru");
        ClientDto client = clientService.createAClient(clientDto);
        System.out.println(client);

    }

}
