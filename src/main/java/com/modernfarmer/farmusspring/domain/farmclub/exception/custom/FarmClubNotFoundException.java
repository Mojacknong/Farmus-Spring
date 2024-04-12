package com.modernfarmer.farmusspring.domain.farmclub.exception.custom;

public class FarmClubNotFoundException extends RuntimeException {

    public FarmClubNotFoundException(String message) {
        super(message);
    }
}
