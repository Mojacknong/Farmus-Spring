package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record HistoryResponseDto(
        List<History.Detail> veggieHistoryList,
        List<History.Detail> farmClubHistoryList
) {
    public static HistoryResponseDto of(History history) {
        return HistoryResponseDto.builder()
                .veggieHistoryList(history.getVeggieHistoryDetails())
                .farmClubHistoryList(history.getFarmClubHistoryDetails())
                .build();
    }
}
