package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

public record SearchFarmClubResponseDto (
        Long farmClubId,
        String farmClubName,
        String farmClubDescription,
        String veggieName,
        String veggieImage,
        String difficulty,
        String startedAt,
        int maxMemberCount
) {

}
