package com.javaacademy.flat_rent.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;
}
