package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostCommentVo;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMissionPostCommentResponseDto(
    List<MissionPostCommentVo> comments
) {
    public static GetMissionPostCommentResponseDto of(List<MissionPostCommentVo> comments) {
        return GetMissionPostCommentResponseDto.builder()
            .comments(comments)
            .build();
    }
}
