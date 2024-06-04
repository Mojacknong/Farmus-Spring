package com.modernfarmer.farmusspring.domain.veggieinfo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;


public record InfoForRegisterVo(

    @JsonProperty("id")
    String _id,
    String name,
    Difficulty difficulty,
    String veggieImage,
    String period
){
}
