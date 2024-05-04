package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyVeggieGardenErrorCode implements ResponseCode {

    NOT_FOUND_ROUTINE(3000, "해당 루틴을 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
