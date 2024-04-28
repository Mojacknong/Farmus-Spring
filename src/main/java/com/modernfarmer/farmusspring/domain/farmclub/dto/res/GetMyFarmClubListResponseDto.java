package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record GetMyFarmClubListResponseDto(
        Long farmClubId,
        String farmClubImage,
        String farmClubName,
        String veggieName
) {
    public static GetMyFarmClubListResponseDto of(Long farmClubId, String farmClubImage, String farmClubName, String veggieName) {
        return GetMyFarmClubListResponseDto.builder()
                .farmClubId(farmClubId)
                .farmClubImage(farmClubImage)
                .farmClubName(farmClubName)
                .veggieName(veggieName)
                .build();
    }

    @QueryProjection
    public GetMyFarmClubListResponseDto(FarmClub farmClub) {
        this(
                farmClub.getId(),
                farmClub.getVeggieImage(),
                farmClub.getName(),
                farmClub.getVeggieName());
    }
}
