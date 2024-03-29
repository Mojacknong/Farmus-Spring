package com.modernfarmer.farmusspring.domain.auth.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ResponseCode {

    WRONG_TOKEN(1001, "토큰 오류입니다.");


    private final int code;
    private final String message;
}
