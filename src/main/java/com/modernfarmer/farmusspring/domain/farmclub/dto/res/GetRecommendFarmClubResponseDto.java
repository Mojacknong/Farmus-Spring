package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetRecommendFarmClubResponseDto(
        List<GetFarmClubResponseDto> farmClubList
) {
    public static GetRecommendFarmClubResponseDto of(List<GetFarmClubResponseDto> farmClubList) {
        return new GetRecommendFarmClubResponseDto(farmClubList);
    }
}
