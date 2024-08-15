package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import lombok.Getter;

@Getter
public class LikeAlreadyExistExcpetion extends MyVeggieGardenBaseException{
    public LikeAlreadyExistExcpetion(String message, MyVeggieGardenErrorCode errorCode) {
        super(message, errorCode);
    }
}
