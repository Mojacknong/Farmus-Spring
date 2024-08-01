package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class DiaryAccessDeniedException extends MyVeggieGardenBaseException{

    public DiaryAccessDeniedException(String message, MyVeggieGardenErrorCode errorCode) {
        super(message, errorCode);
    }
}
