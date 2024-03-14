package com.modernfarmer.farmusspring.domain.test.exception;

import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.test")
public class TestExceptionHandler {

    @ExceptionHandler(TestException.class)
    public BaseResponseDto<?> handleTestException(TestException e, HttpServletRequest request) {
        log.error("TestException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(TestErrorCode.TEST_ERROR, e.getMessage());
    }
}
