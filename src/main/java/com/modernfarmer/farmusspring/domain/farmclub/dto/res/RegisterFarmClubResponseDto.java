package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

import lombok.Builder;

@Builder
public record RegisterFarmClubResponseDto (
        Long userFarmClubId
){
    public static RegisterFarmClubResponseDto of(Long userFarmClubId) {
        return RegisterFarmClubResponseDto.builder()
                .userFarmClubId(userFarmClubId)
                .build();
    }
}
