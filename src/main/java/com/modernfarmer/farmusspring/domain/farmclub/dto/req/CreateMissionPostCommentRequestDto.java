package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

public record CreateMissionPostCommentRequestDto(
        Long missionPostId,
        String content
) {
}
