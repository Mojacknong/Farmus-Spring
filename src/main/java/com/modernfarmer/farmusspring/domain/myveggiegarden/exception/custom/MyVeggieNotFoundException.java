package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class MyVeggieNotFoundException extends MyVeggieGardenBaseException{

    public MyVeggieNotFoundException(String message, MyVeggieGardenErrorCode errorCode) {
        super(message, errorCode);
    }
}
