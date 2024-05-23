package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetMyFarmClubVo(

        String farmClubName,
        String farmClubImage,
        String veggieInfoId,
        Long wholeMemberCount,
        Integer daySinceStart
) {
    public static GetMyFarmClubVo of(BaseInfo baseInfo, Long wholeMemberCount, LocalDate dayRegister) {
        return new GetMyFarmClubVo(
                baseInfo.farmClubName(),
                baseInfo.farmClubImage(),
                baseInfo.veggieInfoId(),
                wholeMemberCount,
                LocalDate.now().compareTo(dayRegister)
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
