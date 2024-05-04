package com.modernfarmer.farmusspring.domain.myveggiegarden.exception;

public class DiaryNotFoundException extends RuntimeException{
    public DiaryNotFoundException(String message) {
        super(message);
    }
}
