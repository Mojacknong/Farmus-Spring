package com.modernfarmer.farmusspring.domain.farmclub.vo;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SuccessFarmClubHistoryVo(
        String date,
        String period
) {
    public static SuccessFarmClubHistoryVo of(String date, String period) {
        return SuccessFarmClubHistoryVo.builder()
                .date(date)
                .period(period)
                .build();
    }
}
