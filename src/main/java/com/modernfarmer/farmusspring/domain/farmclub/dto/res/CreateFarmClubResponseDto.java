package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

public record CreateFarmClubResponseDto (
        Long farmClubId
){
    public static CreateFarmClubResponseDto of(Long farmClubId) {
        return new CreateFarmClubResponseDto(farmClubId);
    }
}
