package com.modernfarmer.farmusspring.domain.farmclub.repository;


import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImagesVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.QGetMissionPostListWithStepCountsAndImagesVo;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QMissionPost.missionPost;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;

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
}
