package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImages;

import java.util.List;

public interface MissionPostRepositoryCustom {

    List<GetMissionPostListWithStepCountsAndImages> getMissionPostStepNumAndImage(Long farmClubId);
}
