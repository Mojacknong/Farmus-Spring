package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyDetailMyVeggieDto {

    String nickname;
    String image;
    String veggieName;
    String birthDay;
    int period;
    Long myVeggieId;

}
