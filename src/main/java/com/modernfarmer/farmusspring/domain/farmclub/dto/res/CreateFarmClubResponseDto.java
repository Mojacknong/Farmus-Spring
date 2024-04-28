package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.Builder;

@Builder
public record CreateFarmClubResponseDto (
        Long farmClubId
){
    public static CreateFarmClubResponseDto of(Long farmClubId) {
        return CreateFarmClubResponseDto.builder()
                .farmClubId(farmClubId)
                .build();
    }
}
