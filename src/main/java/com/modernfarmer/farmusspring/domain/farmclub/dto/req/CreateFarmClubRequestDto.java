package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateFarmClubRequestDto {

    private String farmClubName;
    private String farmClubDescription;
    private int maxMemberCount;
    private String startDate;
    private Long myVeggieId;
    private String veggieInfoId;
}
