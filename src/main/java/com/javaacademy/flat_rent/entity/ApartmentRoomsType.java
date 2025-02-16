package com.javaacademy.flat_rent.entity;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApartmentRoomsType {
    ROOM("только комната"),
    ONE_BEDROOM("1-комнатная"),
    TWO_BEDROOM("2-комнатная"),
    THREE_BEDROOM("3-комнатная"),
    FOUR_AND_MORE_BEDROOM("4 и более комнатная квартира");
    final String name;
}
