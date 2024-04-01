package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public record InfoForRegister (
    String veggieInfoId,
    String veggieName,
    String veggieImage,
    String period,
    Difficulty difficulty
){
}
