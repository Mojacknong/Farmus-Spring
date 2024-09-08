package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

public record CreateMissionPostReportRequestDto(
        Long missionPostId,
        String reason
) {
}
