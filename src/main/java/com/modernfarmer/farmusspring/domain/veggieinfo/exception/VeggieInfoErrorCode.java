package com.modernfarmer.farmusspring.domain.veggieinfo.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VeggieInfoErrorCode implements ResponseCode {

    NotFoundVeggieInfo(5001, "채소 정보를 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
