package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyVeggieGardenErrorCode implements ResponseCode {

    ;

    private final int code;
    private final String message;
}
