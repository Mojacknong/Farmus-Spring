package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostVo;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMissionPostListResponseDto(
    List<MissionPostVo> missionPosts
) {
    public static GetMissionPostListResponseDto of(List<MissionPostVo> missionPosts) {
        return GetMissionPostListResponseDto.builder()
            .missionPosts(missionPosts)
            .build();
    }
}
