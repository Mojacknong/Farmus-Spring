package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.vo.SuccessFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QDiary.diary;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class UserFarmClubRepositoryImpl implements UserFarmClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserFarmClub> findByUserIdAndFarmClubId(Long userId, Long farmClubId) {
        return Optional.ofNullable(queryFactory
                .select(userFarmClub)
                .from(userFarmClub)
                .where(userFarmClub.userId.eq(userId)
                        .and(userFarmClub.farmClub.id.eq(farmClubId)))
                .fetchOne());
    }

    @Override
    public List<Long> findFarmClubIdsByUserId(Long userId) {
        return queryFactory
                .select(userFarmClub.farmClub.id)
                .from(userFarmClub)
                .where(userFarmClub.userId.eq(userId))
                .fetch();
    }

    @Override
    public SuccessFarmClubVo getFarmClubRecord(Long userId, Long farmClubId) {
        return queryFactory
                .select(
                        Projections.constructor(SuccessFarmClubVo.class,
                                userFarmClub.farmClub.name,
                                userFarmClub.farmClub.veggieImage,
                                select(diary.count())
                                        .from(diary)
                                        .where(diary.farmClub.id.eq(userFarmClub.farmClub.id)
                                                .and(diary.myVeggie.user.id.eq(userId))),
                                userFarmClub.missionPosts.size()
                        )
                )
                .from(userFarmClub)
                .where(userFarmClub.farmClub.id.eq(farmClubId).and(userFarmClub.userId.eq(userId)))
                .fetchOne();
    }
}
