package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record GetMissionPostListWithStepCountsAndImages(
        Long count,
        List<String> images
) {
    @QueryProjection
    public GetMissionPostListWithStepCountsAndImages {
    }
}
