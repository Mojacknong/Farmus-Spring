package com.modernfarmer.farmusspring.global.common;

import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponseDto<?> handleInputFieldException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("MethodArgumentNotValidException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        FieldError mainError = e.getFieldErrors().get(0);
        String[] errorInfo = Objects.requireNonNull(mainError.getDefaultMessage()).split(":");
        String message = errorInfo[0];
        return BaseResponseDto.of(ErrorCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(BindException.class)
    public BaseResponseDto<?> handleBindException(BindException e, HttpServletRequest request) {
        log.error("BindException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        FieldError mainError = e.getFieldErrors().get(0);
        String[] errorInfo = Objects.requireNonNull(mainError.getDefaultMessage()).split(":");
        String message = errorInfo[0];
        return BaseResponseDto.of(ErrorCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponseDto<?> handleMethodArgumentTypeMismatchExceptionException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("MethodArgumentTypeMismatchException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(ErrorCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponseDto<?> handleHttpRequestMethodNotSupportedExceptionException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponseDto<?> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("NoHandlerFoundException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(ErrorCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public BaseResponseDto<?> handleHttpClientErrorException(HttpClientErrorException e, HttpServletRequest request) {
        log.error("HttpClientErrorException : {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return BaseResponseDto.of(ErrorCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponseDto<?> unhandledException(Exception e, HttpServletRequest request) {
        log.error("UnhandledException: {} {} errMessage={}\n{}",
                request.getMethod(),
                request.getRequestURI(),
                e.getStackTrace(),
                e.getMessage()
        );
        return BaseResponseDto.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
