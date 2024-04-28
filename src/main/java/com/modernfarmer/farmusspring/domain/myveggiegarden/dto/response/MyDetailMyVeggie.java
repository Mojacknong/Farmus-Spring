package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class MyDetailMyVeggie {

    String nickname;
    String image;
    String veggieName;
    String birthDay;
    int period;
    Long myVeggieId;

}
