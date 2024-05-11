package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.*;
import com.modernfarmer.farmusspring.domain.history.vo.MissionPostHistoryVo;
import com.modernfarmer.farmusspring.domain.history.vo.QMissionPostHistoryVo;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPost.missionPost;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPostComment.missionPostComment;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPostLike.missionPostLike;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QMyVeggie.myVeggie;
import static com.modernfarmer.farmusspring.domain.user.entity.QUser.user;

public class MissionPostRepositoryImpl implements MissionPostRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GetMissionPostListWithStepCountsAndImagesVo> getMissionPostStepNumAndImage(Long farmClubId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .from(missionPost)
                .join(missionPost.userFarmClub, userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(farmClub.id.eq(farmClubId))
                .transform(
                        GroupBy.groupBy(missionPost.stepNum)
                                .list(new QGetMissionPostListWithStepCountsAndImagesVo(
                                        missionPost.stepNum.count(),
                                        GroupBy.list(missionPost.image)
                                ))
                );
    }

    @Override
    public List<MissionPostCommentVo> getMissionPostComment(Long missionPostId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(new QMissionPostCommentVo(missionPost, user))
                .from(missionPost)
                .join(missionPost.userFarmClub, userFarmClub)
                .join(userFarmClub.myVeggie, myVeggie)
                .join(myVeggie.user, user)
                .where(missionPost.id.eq(missionPostId))
                .fetch();
    }

    @Override
    public List<MissionPostVo> getMissionPostList(Long farmClubId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(new QMissionPostVo(
                        missionPost,
                        user,
                        JPAExpressions.select(missionPostLike.count())
                                .from(missionPostLike)
                                .where(missionPostLike.missionPost.eq(missionPost)),
                        JPAExpressions.select(missionPostComment.count())
                                .from(missionPostComment)
                                .where(missionPostComment.missionPost.eq(missionPost))))
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
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

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
}
