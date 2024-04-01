package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

public record InfoForCreateFarmClub(
    String veggieInfoId,
    String veggieName,
    String veggieImage,
    String difficulty
) {
}
