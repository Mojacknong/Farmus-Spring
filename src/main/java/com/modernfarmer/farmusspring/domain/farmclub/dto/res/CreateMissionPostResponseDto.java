package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.Builder;

@Builder
public record CreateMissionPostResponseDto(

        Long missionPostId
) {

    public static CreateMissionPostResponseDto of(Long missionPostId) {
        return CreateMissionPostResponseDto.builder()
                .missionPostId(missionPostId)
                .build();
    }
}
