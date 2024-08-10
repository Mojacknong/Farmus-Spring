package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PRIVATE)
public record FarmClubHistoryIconResponseDto(
        int farmClubHistoryCount,
        Set<History.Icon> farmClubHistoryIcons
) {
    public static FarmClubHistoryIconResponseDto of(History history) {
        return builder()
                .farmClubHistoryCount(history.getFarmClubHistoryDetails().size())
                .farmClubHistoryIcons(history.getFarmClubHistoryIcons())
                .build();
    }
}
