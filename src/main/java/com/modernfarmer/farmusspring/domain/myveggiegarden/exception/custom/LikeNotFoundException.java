package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class LikeNotFoundException extends MyVeggieGardenBaseException {
    public LikeNotFoundException(String message, MyVeggieGardenErrorCode myVeggieGardenErrorCode) {
        super(message, myVeggieGardenErrorCode);
    }
}
