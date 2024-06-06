package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;


import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.test.exception.TestException;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.modernfarmer.farmusspring.domain.test")
public class MyVeggieGardenExceptionHandler {

    @ExceptionHandler(RoutineNotFountException.class)
    public BaseResponseDto<?> handleRoutineNotFoundException(RoutineNotFountException e, HttpServletRequest request) {
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
    @ExceptionHandler(DiaryLikeNotFoundException.class)
    public BaseResponseDto<?> handleDiaryLikeNotFoundException(DiaryLikeNotFoundException e, HttpServletRequest request) {
        log.error("DiaryLikeException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(MyVeggieGardenErrorCode.NOT_FOUND_DIARY_Like, e);
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
