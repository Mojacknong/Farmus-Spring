package com.modernfarmer.farmusspring.domain.history.exception.custom;

import com.modernfarmer.farmusspring.domain.history.exception.HistoryErrorCode;

public class HistoryEntityNotFoundException extends HistoryBaseException{

public HistoryEntityNotFoundException(String message, HistoryErrorCode errorCode) {
        super(message, errorCode);
    }
}
