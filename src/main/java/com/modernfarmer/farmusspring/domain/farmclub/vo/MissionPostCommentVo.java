package com.modernfarmer.farmusspring.domain.farmclub.vo;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostComment;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;

public record MissionPostCommentVo(
        Long missionPostCommentId,
        String nickname,
        String profileImage,
        String date,
        String content,
        Boolean isMyComment
) {
    @QueryProjection
    public MissionPostCommentVo(MissionPostComment missionPostComment, User user, Long myId)
    {
        this(
                missionPostComment.getId(),
                user.getNickname(),
                user.getProfileImage(),
                missionPostComment.getCreatedDate().toString(),
                missionPostComment.getComment(),
                user.getId().equals(myId)
        );
    }
}
