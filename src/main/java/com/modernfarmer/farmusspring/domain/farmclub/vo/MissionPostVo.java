package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;

import static com.modernfarmer.farmusspring.domain.farmclub.util.DateUtil.localDateTimeFormat;

public record MissionPostVo(
        Long missionPostId,
        Long userId,
        int stepNum,
        String nickname,
        String profileImage,
        String date,
        String image,
        String content,
        Long likeCount,
        Long commentCount,
        Boolean isLiked
) {
    @QueryProjection
    public MissionPostVo(MissionPost missionPost, User user, Long likeCount, Long commentCount, Boolean isLiked)
    {
        this(
                missionPost.getId(),
                user.getId(),
                missionPost.getStepNum(),
                user.getNickname(),
                user.getProfileImage(),
                localDateTimeFormat(missionPost.getCreatedDate()),
                missionPost.getImage(),
                missionPost.getContent(),
                likeCount,
                commentCount,
                isLiked
        );
    }
}
