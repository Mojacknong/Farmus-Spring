package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImagesVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostCommentVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.MissionPostVo;
import com.modernfarmer.farmusspring.domain.history.vo.MissionPostHistoryVo;

import java.util.List;

public interface MissionPostRepositoryCustom {

    List<GetMissionPostListWithStepCountsAndImagesVo> getMissionPostStepNumAndImage(Long farmClubId);
    List<MissionPostCommentVo> getMissionPostComment(Long missionPostId);
    List<MissionPostVo> getMissionPostList(Long missionPostId);
    List<MissionPostHistoryVo> getMissionPostHistory(Long missionPostId);
}
