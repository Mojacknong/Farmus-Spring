package com.modernfarmer.farmusspring.domain.user.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;

public class UserNotFoundException extends MyVeggieGardenBaseException {

    public UserNotFoundException(String message, MyVeggieGardenErrorCode errorCode) {
        super(message, errorCode);
    }

}
