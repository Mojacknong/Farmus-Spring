package com.modernfarmer.farmusspring.domain.farmclub.helper;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionPostHelper {

    private final MissionPostRepository missionPostRepository;

    public MissionPost getMissionPost(Long missionPostId) {
        return missionPostRepository.findById(missionPostId)
                .orElseThrow(() -> new FarmClubEntityNotFoundException("존재하지 않는 미션 게시글입니다.", FarmClubErrorCode.MISSION_POST_NOT_FOUND));
    }
}
