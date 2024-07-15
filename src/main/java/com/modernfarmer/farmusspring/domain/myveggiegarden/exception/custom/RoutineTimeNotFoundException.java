package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class RoutineTimeNotFoundException extends  MyVeggieGardenBaseException{

    public RoutineTimeNotFoundException(String message, MyVeggieGardenErrorCode errorCode) {
        super(message, errorCode);
    }
}
