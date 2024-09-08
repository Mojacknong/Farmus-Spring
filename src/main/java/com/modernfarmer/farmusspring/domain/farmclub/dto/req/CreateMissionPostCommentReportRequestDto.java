package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

public record CreateMissionPostCommentReportRequestDto(
        Long missionPostCommentId,
        String reason
) {
}
