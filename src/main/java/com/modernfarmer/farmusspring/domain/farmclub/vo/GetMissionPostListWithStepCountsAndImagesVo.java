package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record GetMissionPostListWithStepCountsAndImagesVo(
        Long count,
        List<String> images
) {
    @QueryProjection
    public GetMissionPostListWithStepCountsAndImagesVo {
    }
}
