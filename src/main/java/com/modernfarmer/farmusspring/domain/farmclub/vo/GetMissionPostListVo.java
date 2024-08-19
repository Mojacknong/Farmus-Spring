package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.querydsl.core.annotations.QueryProjection;

public record GetMissionPostListVo(
        int stepNum,
        String image
) {
    @QueryProjection
    public GetMissionPostListVo {
    }
}
