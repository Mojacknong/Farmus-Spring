package com.modernfarmer.farmusspring.domain.veggieinfo.vo;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;


public record InfoForRegisterVo(
    String _id,
    String name,
    Difficulty difficulty,
    String veggieImage,
    String period
){
}
