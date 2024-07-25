package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record VeggieHistoryListResponseDto(
        List<History.Detail> veggieHistoryList
) {
    public static VeggieHistoryListResponseDto of(List<History.Detail> veggieHistoryList) {
        return builder()
                .veggieHistoryList(veggieHistoryList)
                .build();
    }
}
