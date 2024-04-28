package com.modernfarmer.farmusspring.domain.veggieinfo.exception.custom;

public class VeggieInfoNotFoundException extends RuntimeException {

    public VeggieInfoNotFoundException(String message) {
        super(message);
    }
}
