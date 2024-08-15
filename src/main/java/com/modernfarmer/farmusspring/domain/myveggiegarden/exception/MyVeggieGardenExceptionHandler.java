package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;


import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.LikeNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.RoutineNotFoundException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.myveggiegarden.controller")
public class MyVeggieGardenExceptionHandler {

    @ExceptionHandler(RoutineNotFoundException.class)
    public BaseResponseDto<?> handleRoutineNotFoundException(RoutineNotFoundException e, HttpServletRequest request) {
        log.error("RoutineException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(MyVeggieGardenErrorCode.NOT_FOUND_ROUTINE, e);
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    public BaseResponseDto<?> handleDiaryNotFoundException(DiaryNotFoundException e, HttpServletRequest request) {
        log.error("DiaryException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(MyVeggieGardenErrorCode.NOT_FOUND_DIARY, e);
    }

    @ExceptionHandler(DiaryCommentNotFoundException.class)
    public BaseResponseDto<?> handleDiaryCommentNotFoundException(DiaryCommentNotFoundException e, HttpServletRequest request) {
        log.error("DiaryCommentException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(MyVeggieGardenErrorCode.NOT_FOUND_DIARY_COMMENT, e);
    }

    @ExceptionHandler(MyVeggieGardenBaseException.class)
    public BaseResponseDto<?> handleMyVeggieException(MyVeggieGardenBaseException e, HttpServletRequest request) {
        log.error("DiaryLikeException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(e.getErrorCode(), e.getMessage());
    }
}
