package com.modernfarmer.farmusspring.domain.farmclub.exception;

import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubBaseException;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.EntityNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.farmclub")
public class FarmClubExceptionHandler {

    @ExceptionHandler(FarmClubBaseException.class)
    public BaseResponseDto<?> handleFarmClubNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("FarmClubNotFoundException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(e.getErrorCode(), e.getMessage());
    }
}
