package com.modernfarmer.farmusspring.domain.farmclub.exception.custom;

import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import lombok.Getter;

@Getter
public class FarmClubBaseException extends RuntimeException {

    private final FarmClubErrorCode errorCode;

    public FarmClubBaseException(String message, FarmClubErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
