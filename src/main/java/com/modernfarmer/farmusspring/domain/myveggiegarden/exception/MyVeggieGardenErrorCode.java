package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyVeggieGardenErrorCode implements ResponseCode {

    NOT_FOUND_DIARY(3000, "해당 일기가 존재하지 않습니다.");

    private final int code;
    private final String message;
}
