package com.gotravel.flightpostbookingservice.exception;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ErrorType {

    private String exception;
    private String errorMessage;
    private int statusCode;
}
