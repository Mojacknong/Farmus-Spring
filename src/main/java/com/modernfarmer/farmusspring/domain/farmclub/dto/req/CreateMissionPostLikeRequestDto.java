package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

public record CreateMissionPostLikeRequestDto(
    Long missionPostId,
    Long userId
) {
}
