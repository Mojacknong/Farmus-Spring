package com.modernfarmer.farmusspring.domain.user.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;

public class UserNotFoundException extends UserBaseException {

    public UserNotFoundException(String message, UserErrorCode errorCode) {
        super(message, errorCode);
    }

}
