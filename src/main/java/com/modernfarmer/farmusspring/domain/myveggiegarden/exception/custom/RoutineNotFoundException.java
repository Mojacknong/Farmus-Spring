package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class RoutineNotFoundException extends MyVeggieGardenBaseException {


    public RoutineNotFoundException(String message, MyVeggieGardenErrorCode errorCode) {
            super(message, errorCode);
        }

}
