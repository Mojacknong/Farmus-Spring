package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostCommentVo;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMissionPostCommentResponseDto(
    Boolean isMyPost,
    List<MissionPostCommentVo> comments
) {
    public static GetMissionPostCommentResponseDto of(Boolean isMyPost, List<MissionPostCommentVo> comments) {
        return GetMissionPostCommentResponseDto.builder()
            .isMyPost(isMyPost)
            .comments(comments)
            .build();
    }
}
