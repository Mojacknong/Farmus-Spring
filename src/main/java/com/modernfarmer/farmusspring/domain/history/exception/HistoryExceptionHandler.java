package com.modernfarmer.farmusspring.domain.history.exception;

import com.modernfarmer.farmusspring.domain.history.exception.custom.HistoryEntityNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.history")
public class HistoryExceptionHandler {

    @ExceptionHandler(HistoryEntityNotFoundException.class)
    public BaseResponseDto<?> handleHistoryNotFoundException(HistoryEntityNotFoundException e, HttpServletRequest request) {
        log.error("HistoryEntityNotFoundException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(e.getErrorCode(), e.getMessage());
    }
}
