package com.modernfarmer.farmusspring.domain.user.exception.custom;

import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;
import lombok.Getter;

@Getter
public class UserBaseException extends RuntimeException{

    private final UserErrorCode errorCode;
    public  UserBaseException(String message, UserErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
