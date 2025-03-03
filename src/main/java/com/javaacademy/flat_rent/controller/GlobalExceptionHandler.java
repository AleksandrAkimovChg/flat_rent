package com.javaacademy.flat_rent.controller;

import com.javaacademy.flat_rent.exception.BookingIsNotAvailableException;
import com.javaacademy.flat_rent.exception.BookingStartDayLaterDayEndException;
import com.javaacademy.flat_rent.exception.PageNumberLessZeroException;
import com.javaacademy.flat_rent.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PageNumberLessZeroException.class)
    public ResponseEntity<String> handePageNumberException(PageNumberLessZeroException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            BookingIsNotAvailableException.class,
            BookingStartDayLaterDayEndException.class,
            BookingStartDayLaterDayEndException.class
    })
    public ResponseEntity<String> conflictException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
