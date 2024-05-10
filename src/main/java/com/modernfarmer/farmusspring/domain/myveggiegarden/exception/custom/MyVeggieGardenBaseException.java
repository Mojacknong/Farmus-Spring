package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;


public class MyVeggieGardenBaseException extends RuntimeException{

    private final MyVeggieGardenErrorCode errorCode;
    public MyVeggieGardenBaseException(String message, MyVeggieGardenErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
