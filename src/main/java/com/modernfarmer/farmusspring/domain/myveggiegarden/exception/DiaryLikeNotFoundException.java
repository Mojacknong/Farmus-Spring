package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;

public class DiaryLikeNotFoundException extends MyVeggieGardenBaseException {
    public DiaryLikeNotFoundException(String message, MyVeggieGardenErrorCode myVeggieGardenErrorCode) {
        super(message, myVeggieGardenErrorCode);
    }
}
