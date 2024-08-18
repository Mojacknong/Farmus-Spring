package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;

public record CreateMissionPostRequestDto(
        // 사진, 내용, 스텝번호
        Long farmClubId,
        String content
) {

    public MissionPost toEntity(UserFarmClub userFarmClub, String imageUrl, int stepNum) {
        return MissionPost.createMissionPost(this.content, stepNum, imageUrl, userFarmClub);
    }
}
