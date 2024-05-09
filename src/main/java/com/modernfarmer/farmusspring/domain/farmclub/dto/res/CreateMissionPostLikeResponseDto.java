package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.Builder;

@Builder
public record CreateMissionPostLikeResponseDto(
        Long missionPostLikeId
) {

    public static CreateMissionPostLikeResponseDto of(Long missionPostLikeId) {
        return CreateMissionPostLikeResponseDto.builder()
                .missionPostLikeId(missionPostLikeId)
                .build();
    }
}
