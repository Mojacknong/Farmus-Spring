package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record CreateFarmClubRequestDto (

    String farmClubName,
    String farmClubDescription,
    int maxMemberCount,
    String startDate,
    Long myVeggieId,
    String veggieInfoId
) {
    public static CreateFarmClubRequestDto of(String farmClubName, String farmClubDescription, int maxMemberCount, String startDate, Long myVeggieId, String veggieInfoId) {
        return new CreateFarmClubRequestDto(farmClubName, farmClubDescription, maxMemberCount, startDate, myVeggieId, veggieInfoId);
    }
}
