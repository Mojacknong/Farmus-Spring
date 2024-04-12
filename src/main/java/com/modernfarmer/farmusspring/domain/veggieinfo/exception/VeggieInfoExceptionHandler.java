package com.modernfarmer.farmusspring.domain.veggieinfo.exception;

import com.modernfarmer.farmusspring.domain.veggieinfo.exception.custom.VeggieInfoNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class VeggieInfoExceptionHandler {
    @ExceptionHandler(VeggieInfoNotFoundException.class)
    public BaseResponseDto<?> handleVeggieInfoNotFoundException(VeggieInfoNotFoundException e, HttpServletRequest request) {
        log.error("VeggieInfoNotFoundException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(VeggieInfoErrorCode.NotFoundVeggieInfo, e.getMessage());
    }
}
