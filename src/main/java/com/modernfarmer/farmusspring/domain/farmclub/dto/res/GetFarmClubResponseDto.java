package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import lombok.Builder;

import java.util.List;

@Builder
public record GetFarmClubResponseDto (
        Long farmClubId,
        String farmClubName,
        String farmClubDescription,
        String veggieName,
        String veggieImage,
        String startedAt,
        int maxMemberCount,
        int currentMemberCount,
        List<String> help
){
    public static GetFarmClubResponseDto of(FarmClub farmClub, int currentMemberCount, List<String> help) {
        return GetFarmClubResponseDto.builder()
                .farmClubId(farmClub.getId())
                .farmClubName(farmClub.getName())
                .farmClubDescription(farmClub.getDescription())
                .veggieName(farmClub.getVeggieName())
                .veggieImage(farmClub.getVeggieImage())
                .startedAt(String.valueOf(farmClub.getStartedAt()))
                .maxMemberCount(farmClub.getMaxUser())
                .currentMemberCount(currentMemberCount)
                .help(help)
                .build();
    }
}
