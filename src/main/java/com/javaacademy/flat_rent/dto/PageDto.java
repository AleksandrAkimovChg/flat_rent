package com.javaacademy.flat_rent.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> content;
    private Integer number;
    private Integer size;
    private Integer numberOfElements;
    private Integer totalPages;
    private Integer totalElements;
}
