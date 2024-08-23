package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record GetMyFarmClubVo(
        Long farmClubId,
        String farmClubName,
        String farmClubImage,
        String veggieInfoId,
        Long wholeMemberCount,
        Integer currentStep,
        Long daySinceStart
) {
    public static GetMyFarmClubVo of(BaseInfo baseInfo, Long wholeMemberCount, Integer currentStep,LocalDate dayRegister) {
        return new GetMyFarmClubVo(
                baseInfo.farmClubId(),
                baseInfo.farmClubName(),
                baseInfo.farmClubImage(),
                baseInfo.veggieInfoId(),
                wholeMemberCount,
                currentStep,
                ChronoUnit.DAYS.between(dayRegister, LocalDate.now())
        );
    }

    public record BaseInfo(
            Long farmClubId,
            String farmClubName,
            String farmClubImage,
            String veggieInfoId
    ) {

        @QueryProjection
        public BaseInfo(FarmClub farmClub) {
            this(
                    farmClub.getId(),
                    farmClub.getName(),
                    farmClub.getVeggieImage(),
                    farmClub.getVeggieInfoId()
            );
        }
    }
}
