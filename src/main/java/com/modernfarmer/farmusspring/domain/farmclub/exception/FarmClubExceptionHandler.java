package com.modernfarmer.farmusspring.domain.farmclub.exception;

import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.farmclub")
public class FarmClubExceptionHandler {

    @ExceptionHandler(FarmClubNotFoundException.class)
    public BaseResponseDto<?> handleFarmClubNotFoundException(FarmClubNotFoundException e, HttpServletRequest request) {
        log.error("FarmClubNotFoundException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(FarmClubErrorCode.FARM_CLUB_NOT_FOUND, e.getMessage());
    }
}
