package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.History;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record VeggieHistoryIconResponseDto(
        int veggieHistoryCount,
        List<History.Icon> veggieHistoryIcons
) {
    public static VeggieHistoryIconResponseDto of(History history) {
        return builder()
                .veggieHistoryCount(history.getVeggieHistoryDetails().size())
                .veggieHistoryIcons(history.getVeggieHistoryIcons())
                .build();
    }
}
