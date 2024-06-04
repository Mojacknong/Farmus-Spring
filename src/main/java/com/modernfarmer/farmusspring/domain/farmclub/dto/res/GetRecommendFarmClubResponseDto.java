package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetRecommendFarmClubResponseDto(
        GetFarmClubResponseDto recFirst,
        GetFarmClubResponseDto recSecond
) {
    public static GetRecommendFarmClubResponseDto of(GetFarmClubResponseDto recFirst, GetFarmClubResponseDto recSecond) {
        return GetRecommendFarmClubResponseDto.builder()
                .recFirst(recFirst)
                .recSecond(recSecond)
                .build();
    }
}
