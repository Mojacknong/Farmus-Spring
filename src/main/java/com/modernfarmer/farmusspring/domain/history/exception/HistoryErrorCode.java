package com.modernfarmer.farmusspring.domain.history.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HistoryErrorCode implements ResponseCode {

    ENTITY_NOT_FOUND(6001, "엔티티를 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
