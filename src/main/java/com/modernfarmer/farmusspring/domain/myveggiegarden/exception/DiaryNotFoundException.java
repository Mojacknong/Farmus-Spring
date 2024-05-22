package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;

public class DiaryNotFoundException extends MyVeggieGardenBaseException {
    public DiaryNotFoundException(String message, MyVeggieGardenErrorCode myVeggieGardenErrorCode) {
        super(message, myVeggieGardenErrorCode);
    }
}
