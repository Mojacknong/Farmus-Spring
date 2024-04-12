package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;

public record GetMyFarmClubListResponseDto(
        Long farmClubId,
        String farmClubImage,
        String farmClubName,
        String veggieName
) {
    public static GetMyFarmClubListResponseDto of(Long farmClubId, String farmClubImage, String farmClubName, String veggieName) {
        return new GetMyFarmClubListResponseDto(farmClubId, farmClubImage, farmClubName, veggieName);
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
