package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;

public record MissionPostCommentVo(
        Long missionPostCommentId,
        String nickname,
        String profileImage,
        String date,
        String content
) {
    @QueryProjection
    public MissionPostCommentVo(MissionPost missionPost, User user)
    {
        this(
                missionPost.getId(),
                user.getNickname(),
                user.getProfileImage(),
                missionPost.getCreatedDate().toString(),
                missionPost.getContent()
        );
    }
}
