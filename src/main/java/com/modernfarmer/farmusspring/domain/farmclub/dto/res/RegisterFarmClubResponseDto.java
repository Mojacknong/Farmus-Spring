package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

public record RegisterFarmClubResponseDto (
        Long userFarmClubId
){
    public static RegisterFarmClubResponseDto of(Long userFarmClubId) {
        return new RegisterFarmClubResponseDto(userFarmClubId);
    }
}
