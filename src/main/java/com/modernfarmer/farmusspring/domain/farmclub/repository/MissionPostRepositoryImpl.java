package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.*;
import com.modernfarmer.farmusspring.domain.history.vo.MissionPostHistoryVo;
import com.modernfarmer.farmusspring.domain.history.vo.QMissionPostHistoryVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPost.missionPost;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPostComment.missionPostComment;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPostLike.missionPostLike;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QMyVeggie.myVeggie;
import static com.modernfarmer.farmusspring.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class MissionPostRepositoryImpl implements MissionPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetMissionPostListVo> getMissionPostStepNumAndImage(Long farmClubId) {

        return queryFactory
                .select(Projections.constructor(
                        GetMissionPostListVo.class,
                        missionPost.stepNum,
                        missionPost.image))
                .from(missionPost)
                .join(missionPost.userFarmClub, userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(farmClub.id.eq(farmClubId))
                .fetch();
    }

    @Override
    public List<MissionPostCommentVo> getMissionPostComment(Long missionPostId, Long userId) {

        return queryFactory
                .select(new QMissionPostCommentVo(missionPostComment, user, Expressions.constant(userId)))
                .from(missionPostComment)
                .join(missionPostComment.missionPost, missionPost)
                .where(missionPost.id.eq(missionPostId))
                .fetch();
    }

    @Override
    public List<MissionPostVo> getMissionPostList(Long userId, Long farmClubId) {

        return queryFactory
                .select(new QMissionPostVo(
                        missionPost,
                        user,
                        JPAExpressions.select(missionPostLike.count())
                                .from(missionPostLike)
                                .where(missionPostLike.missionPost.eq(missionPost)),
                        JPAExpressions.select(missionPostComment.count())
                                .from(missionPostComment)
                                .where(missionPostComment.missionPost.eq(missionPost)),
                        JPAExpressions.selectOne()
                                .from(missionPostLike)
                                .where(missionPostLike.missionPost.eq(missionPost)
                                        .and(missionPostLike.user.id.eq(userId)))
                                .exists()
                        ))
                .from(missionPost)
                .join(missionPost.userFarmClub, userFarmClub)
                .join(userFarmClub.myVeggie, myVeggie)
                .join(myVeggie.user, user)
                .where(farmClub.id.eq(farmClubId))
                .orderBy(missionPost.createdDate.desc())
                .fetch();
    }

    @Override
    public List<MissionPostHistoryVo> getMissionPostHistory(Long userFarmClubId) {

        return queryFactory
                .select(new QMissionPostHistoryVo(
                        missionPost.stepNum,
                        missionPost.image,
                        missionPost.content,
                        missionPost.createdDate.stringValue()
                ))
                .from(missionPost)
                .join(missionPost.userFarmClub, userFarmClub)
                .where(userFarmClub.id.eq(userFarmClubId))
                .orderBy(missionPost.stepNum.asc())
                .fetch();
    }

    @Override
    public void deleteMissionPostLike(Long userId, Long missionPostId) {
        queryFactory
                .delete(missionPostLike)
                .where(missionPostLike.user.id.eq(userId)
                        .and(missionPostLike.missionPost.id.eq(missionPostId)))
                .execute();
    }
}
