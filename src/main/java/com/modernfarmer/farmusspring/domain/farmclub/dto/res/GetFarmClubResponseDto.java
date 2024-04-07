package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public record GetFarmClubResponseDto (
        Long farmClubId,
        String farmClubName,
        String farmClubDescription,
        String veggieName,
        String veggieImage,
        String startedAt,
        int maxMemberCount,
        int currentMemberCount,
        String preparation
){

}
