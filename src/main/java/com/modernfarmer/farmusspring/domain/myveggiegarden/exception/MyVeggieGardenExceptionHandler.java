package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

import com.modernfarmer.farmusspring.domain.test.exception.TestException;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;
import com.modernfarmer.farmusspring.domain.user.exception.UserNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.test")
public class MyVeggieGardenExceptionHandler {

    @ExceptionHandler(RoutineNotFountException.class)
    public BaseResponseDto<?> handleRoutineNotFoundException(TestException e, HttpServletRequest request) {
        log.error("RoutineException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(UserErrorCode.NOT_FOUND_USER, e);
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    public BaseResponseDto<?> handleDiaryNotFoundException(TestException e, HttpServletRequest request) {
        log.error("DiaryException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(UserErrorCode.NOT_FOUND_USER, e);
    }



}
