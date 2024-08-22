package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.vo.SuccessFarmClubVo;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SuccessFarmClubResponseDto(
        String farmClubName,
        String veggieImage,
        Long date,
        String period,
        Long diaryCount,
        Long missionPostCount
) {
    public static SuccessFarmClubResponseDto of(SuccessFarmClubVo info, Long date, String period) {
        return SuccessFarmClubResponseDto.builder()
                .farmClubName(info.farmClubName())
                .veggieImage(info.veggieImage())
                .date(date)
                .period(period)
                .diaryCount(info.diaryCount())
                .missionPostCount(info.missionPostCount())
                .build();
    }
}
