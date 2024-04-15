package com.modernfarmer.farmusspring.domain.farmclub.exception.custom;

import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;

public class EntityNotFoundException extends FarmClubBaseException {

    public EntityNotFoundException(String message, FarmClubErrorCode errorCode) {
        super(message, errorCode);
    }
}
