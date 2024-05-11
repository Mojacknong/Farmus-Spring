package com.modernfarmer.farmusspring.domain.history.exception.custom;

import com.modernfarmer.farmusspring.domain.history.exception.HistoryErrorCode;
import lombok.Getter;

@Getter
public class HistoryBaseException extends RuntimeException {
    private final HistoryErrorCode errorCode;

    public HistoryBaseException(String message, HistoryErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
