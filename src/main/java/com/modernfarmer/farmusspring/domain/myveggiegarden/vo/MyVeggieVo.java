package com.modernfarmer.farmusspring.domain.myveggiegarden.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record MyVeggieVo(
        Long myVeggieId,
        String nickname
) {

    @QueryProjection
    public MyVeggieVo {}

    public static MyVeggieVo of(Long myVeggieId, String nickname) {
        return new MyVeggieVo(myVeggieId, nickname);
    }
}
