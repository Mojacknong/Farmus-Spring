package com.modernfarmer.farmusspring.domain.farmclub.vo;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SuccessFarmClubVo(
        String farmClubName,
        String veggieImage,
        Long diaryCount,
        Long missionPostCount
) {
    public static SuccessFarmClubVo of(String farmClubName, String veggieImage, Long diaryCount, Long missionPostCount) {
        return SuccessFarmClubVo.builder()
                .farmClubName(farmClubName)
                .veggieImage(veggieImage)
                .diaryCount(diaryCount)
                .missionPostCount(missionPostCount)
                .build();
    }
}
