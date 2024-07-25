package com.modernfarmer.farmusspring.domain.history.dto.res;

import com.modernfarmer.farmusspring.domain.history.document.HistoryFarmClubDetail;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FarmClubHistoryDetailResponseDto(
        List<HistoryFarmClubDetail.HistoryClubPost> missionPosts
) {
    public static FarmClubHistoryDetailResponseDto of(List<HistoryFarmClubDetail.HistoryClubPost> missionPosts) {
        return builder()
                .missionPosts(missionPosts)
                .build();
    }
}
