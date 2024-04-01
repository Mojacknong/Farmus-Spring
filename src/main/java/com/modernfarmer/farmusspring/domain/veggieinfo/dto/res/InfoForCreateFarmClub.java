package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;

public record InfoForCreateFarmClub(
    String veggieInfoId,
    String veggieName,
    String veggieImage,
    Difficulty difficulty
) {
}
