package com.modernfarmer.farmusspring.domain.myveggiegarden.vo;

import com.querydsl.core.annotations.QueryProjection;

public record MyVeggieVo(
        Long myVeggieId,
        String nickname
) {

    @QueryProjection
    public MyVeggieVo {}
}
