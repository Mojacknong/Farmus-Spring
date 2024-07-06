package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.querydsl.core.annotations.QueryProjection;

public record GetMissionPostListVo(
        Long stepNum,
        String image
) {
    @QueryProjection
    public GetMissionPostListVo {
    }
}
