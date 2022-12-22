package com.lunatech.assessment.imdb.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImdbNotFoundException extends RuntimeException{
    final String errorMessage; 
    String detailMessage; 

}

