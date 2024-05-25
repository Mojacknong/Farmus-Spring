package com.modernfarmer.farmusspring.domain.history.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HistoryDetailVo(
        String image,
        String historyName,
        String name,
        String period
) {
    @QueryProjection
    public HistoryDetailVo{}

    public static HistoryDetailVo of(String image, String historyName, String name, String period) {
        return HistoryDetailVo.builder()
                .image(image)
                .historyName(historyName)
                .name(name)
                .period(period)
                .build();
    }
}
