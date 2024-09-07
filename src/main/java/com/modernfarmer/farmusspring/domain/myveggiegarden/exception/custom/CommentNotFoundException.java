package com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom;

import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;

public class CommentNotFoundException extends MyVeggieGardenBaseException{
    public CommentNotFoundException(String message, MyVeggieGardenErrorCode myVeggieGardenErrorCode) {
        super(message, myVeggieGardenErrorCode);
    }
}
