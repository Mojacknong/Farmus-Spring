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
        List<Icon> veggieHistoryIcons,
        List<Icon> farmClubHistoryIcons
) {
    public record Icon(
            String url,
            String backgroundColor
    ) {

    }

    public static HistoryResponseDto of(History history, List<Icon> veggieHistoryIcons, List<Icon> farmClubHistoryIcons) {
        return builder()
                .historyId(history.getId().toHexString())
                .veggieHistoryCount(history.getVeggieHistoryDetails().size())
                .farmClubHistoryCount(history.getFarmClubHistoryDetails().size())
                .veggieHistoryIcons(veggieHistoryIcons)
                .farmClubHistoryIcons(farmClubHistoryIcons)
                .build();
    }
}
