package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class SelectMyVeggieProfileResponse {

    private String nickname;
    private String veggieName;
    private String veggieImage;
    private String createdVeggie;
    private int period;
    private int step;
    private int stepCount;
}
