package com.modernfarmer.farmusspring.domain.farmclub.dto.req;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;

public record CreateMissionPostRequestDto(
        // 사진, 내용, 스텝번호
        Long userFarmClubId,
        String content,
        Long stepNum
) {

    public MissionPost toEntity(UserFarmClub userFarmClub, String imageUrl) {
        return MissionPost.createMissionPost(this.content, this.stepNum, imageUrl, userFarmClub);
    }
}
