package com.modernfarmer.farmusspring.domain.farmclub.dto.res;


import lombok.Builder;

@Builder
public record CreateMissionPostCommentResponseDto(
        Long missionPostCommentId
) {

    public static CreateMissionPostCommentResponseDto of(Long missionPostCommentId) {
        return CreateMissionPostCommentResponseDto.builder()
                .missionPostCommentId(missionPostCommentId)
                .build();
    }
}
