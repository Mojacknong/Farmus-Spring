package com.modernfarmer.farmusspring.domain.test.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestErrorCode implements ResponseCode {

    TEST_ERROR(9999, "테스트 에러입니다.");

    private final int code;
    private final String message;
}
