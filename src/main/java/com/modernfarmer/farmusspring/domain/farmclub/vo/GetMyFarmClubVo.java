package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;

public record GetMyFarmClubVo(

        String farmClubName,
        String farmClubImage,
        String veggieInfoId,
        Long wholeMemberCount,
        Integer daySinceStart
) {
    public static GetMyFarmClubVo of(BaseInfo baseInfo, Long wholeMemberCount, Integer daySinceStart) {
        return new GetMyFarmClubVo(
                baseInfo.farmClubName(),
                baseInfo.farmClubImage(),
                baseInfo.veggieInfoId(),
                wholeMemberCount,
                daySinceStart
        );
    }

    public record BaseInfo(
            String farmClubName,
            String farmClubImage,
            String veggieInfoId
    ) {

        @QueryProjection
        public BaseInfo(FarmClub farmClub) {
            this(
                    farmClub.getName(),
                    farmClub.getVeggieImage(),
                    farmClub.getVeggieInfoId()
            );
        }
    }
}
