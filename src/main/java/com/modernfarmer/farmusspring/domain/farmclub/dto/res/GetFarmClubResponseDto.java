package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;

import java.util.List;

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
        return new GetFarmClubResponseDto(
                farmClub.getId(),
                farmClub.getName(),
                farmClub.getDescription(),
                farmClub.getVeggieName(),
                farmClub.getVeggieImage(),
                farmClub.getStartedAt().toString(),
                farmClub.getMaxUser(),
                currentMemberCount,
                help
        );
    }
}
