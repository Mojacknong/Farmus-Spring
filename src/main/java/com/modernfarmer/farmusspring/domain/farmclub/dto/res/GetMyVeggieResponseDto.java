package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetMyVeggieResponseDto(
        Long myVeggieId,
        String nickname
) {

    public static GetMyVeggieResponseDto of(Long myVeggieId, String nickname) {
        return GetMyVeggieResponseDto.builder()
                .myVeggieId(myVeggieId)
                .nickname(nickname)
                .build();
    }
}
