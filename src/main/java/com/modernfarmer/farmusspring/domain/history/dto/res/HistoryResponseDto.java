package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record HistoryResponseDto(
        String historyId,
        int veggieHistoryCount,
        int farmClubHistoryCount,
        List<History.Icon> veggieHistoryIcons,
        List<History.Icon> farmClubHistoryIcons
) {
    public static HistoryResponseDto of(History history) {
        return builder()
                .historyId(history.getId().toHexString())
                .veggieHistoryCount(history.getVeggieHistoryDetails().size())
                .farmClubHistoryCount(history.getFarmClubHistoryDetails().size())
                .veggieHistoryIcons(history.getVeggieHistoryIcons())
                .farmClubHistoryIcons(history.getFarmClubHistoryIcons())
                .build();
    }
}
