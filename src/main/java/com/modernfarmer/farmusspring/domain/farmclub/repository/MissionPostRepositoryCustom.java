package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImagesVo;

import java.util.List;

public interface MissionPostRepositoryCustom {

    List<GetMissionPostListWithStepCountsAndImagesVo> getMissionPostStepNumAndImage(Long farmClubId);
}
