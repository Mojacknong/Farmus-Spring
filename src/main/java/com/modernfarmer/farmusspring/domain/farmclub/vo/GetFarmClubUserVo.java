package com.modernfarmer.farmusspring.domain.farmclub.vo;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetFarmClubUserVo(
        String nickname,
        String profileImage
) {
    public static GetFarmClubUserVo of(String nickname, String profileImage) {
        return GetFarmClubUserVo.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
