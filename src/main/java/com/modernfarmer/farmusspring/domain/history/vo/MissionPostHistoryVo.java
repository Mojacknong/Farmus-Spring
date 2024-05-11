package com.modernfarmer.farmusspring.domain.history.vo;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MissionPostHistoryVo(
        Long stepNum,
        String image,
        String content,
        String date
) {

    @QueryProjection
    public MissionPostHistoryVo {
    }

    public static MissionPostHistoryVo of(Long stepNum, String image, String content, String date) {
        return MissionPostHistoryVo.builder()
                .stepNum(stepNum)
                .image(image)
                .content(content)
                .date(date)
                .build();
    }
}
