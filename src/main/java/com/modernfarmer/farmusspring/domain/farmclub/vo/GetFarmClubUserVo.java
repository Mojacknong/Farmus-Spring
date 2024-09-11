package com.modernfarmer.farmusspring.domain.farmclub.vo;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetFarmClubUserVo(
        Long userId,
        String nickname,
        String profileImage
) {
    public static GetFarmClubUserVo of(Long userId, String nickname, String profileImage) {
        return GetFarmClubUserVo.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
