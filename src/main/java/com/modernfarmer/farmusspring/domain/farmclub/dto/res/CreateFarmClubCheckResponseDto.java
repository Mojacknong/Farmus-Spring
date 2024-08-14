package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateFarmClubCheckResponseDto(
        Boolean isPossible,
        Long reason
) {
    public static CreateFarmClubCheckResponseDto of(Boolean isPossible, Long reason) {
        return CreateFarmClubCheckResponseDto.builder()
                .isPossible(isPossible)
                .reason(reason)
                .build();
    }
}
