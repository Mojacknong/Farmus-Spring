package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.Builder;

@Builder
public record CreateMissionPostResponseDto(
        Long missionPostId,
        Boolean isLastStep
) {

    public static CreateMissionPostResponseDto of(Long missionPostId, Boolean isLastStep) {
        return CreateMissionPostResponseDto.builder()
                .missionPostId(missionPostId)
                .isLastStep(isLastStep)
                .build();
    }
}
