package com.modernfarmer.farmusspring.domain.auth.exception;

import com.modernfarmer.farmusspring.domain.test.exception.TestException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.test")
public class AuthExceptionHandler {

    @ExceptionHandler(AuthRefreshTokenValidateException.class)
    public BaseResponseDto<?> handleAuthRefreshTokenValidateException(TestException e, HttpServletRequest request) {
        log.error("TestException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(AuthErrorCode.WRONG_TOKEN, e);
    }
}
