package com.modernfarmer.farmusspring.domain.farmclub.dto.res;

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
    public static GetFarmClubResponseDto of(Long farmClubId, String farmClubName, String farmClubDescription, String veggieName, String veggieImage, String startedAt, int maxMemberCount, int currentMemberCount, String preparation) {
        return new GetFarmClubResponseDto(farmClubId, farmClubName, farmClubDescription, veggieName, veggieImage, startedAt, maxMemberCount, currentMemberCount, preparation);
    }
}
