package com.modernfarmer.farmusspring.domain.farmclub.exception.custom;

import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;

public class FarmClubEntityNotFoundException extends FarmClubBaseException {

    public FarmClubEntityNotFoundException(String message, FarmClubErrorCode errorCode) {
        super(message, errorCode);
    }
}
