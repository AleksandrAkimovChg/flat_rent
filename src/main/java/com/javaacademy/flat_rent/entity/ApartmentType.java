package com.javaacademy.flat_rent.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApartmentType {

    ONLY_ROOM("комната"),
    ONE_BEDROOM("квартира с 1-ой комнатой"),
    TWO_BEDROOM("квартира с 2-я комнатами"),
    THREE_BEDROOM("квартира с 3-я комнатами"),
    FOUR_AND_MORE_BEDROOM("квартира с 4-я и более комнатами");

    private final String description;
}
