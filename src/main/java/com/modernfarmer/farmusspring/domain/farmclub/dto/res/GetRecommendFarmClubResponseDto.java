package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetRecommendFarmClubResponseDto(
        GetFarmClubResponseDto recFirst,
        GetFarmClubResponseDto recSecond,
        String nickname
) {
    public static GetRecommendFarmClubResponseDto of(GetFarmClubResponseDto recFirst, GetFarmClubResponseDto recSecond, String nickname) {
        return GetRecommendFarmClubResponseDto.builder()
                .recFirst(recFirst)
                .recSecond(recSecond)
                .nickname(nickname)
                .build();
    }
}
