package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FarmClubHistoryListResponseDto(
        List<History.Detail> farmClubHistoryList
) {
    public static FarmClubHistoryListResponseDto of(List<History.Detail> farmClubHistoryList) {
        return builder()
                .farmClubHistoryList(farmClubHistoryList)
                .build();
    }
}
