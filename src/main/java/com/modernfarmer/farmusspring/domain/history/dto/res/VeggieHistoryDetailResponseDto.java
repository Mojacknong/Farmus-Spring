package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record VeggieHistoryDetailResponseDto(
    List<HistoryVeggieDetail.HistoryPost> diaries,
    HistoryVeggieDetail.HistoryPost farmResult
) {
    public static VeggieHistoryDetailResponseDto of(List<HistoryVeggieDetail.HistoryPost> diaries, HistoryVeggieDetail.HistoryPost farmResult) {
        return builder()
            .diaries(diaries)
            .farmResult(farmResult)
            .build();
    }
}
