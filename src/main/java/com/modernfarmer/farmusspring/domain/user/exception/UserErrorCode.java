package com.modernfarmer.farmusspring.domain.user.exception;

import com.modernfarmer.farmusspring.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ResponseCode {

    NOT_FOUND_USER(1000, "유저 오류입니다.");

    private final int code;
    private final String message;
}
