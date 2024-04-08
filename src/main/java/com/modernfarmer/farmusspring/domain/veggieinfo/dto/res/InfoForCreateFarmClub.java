package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;

public record InfoForCreateFarmClub(
    String _id,
    String name,
    String veggieImage,
    Difficulty difficulty
) {
}
