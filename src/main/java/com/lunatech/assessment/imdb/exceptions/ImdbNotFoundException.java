package com.lunatech.assessment.imdb.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImdbNotFoundException extends RuntimeException{
    final String errorMessage; 

    @SuppressWarnings("java:S1165")
    String detailMessage; 

}

