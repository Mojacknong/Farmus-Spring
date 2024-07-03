package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;

@RequiredArgsConstructor
public class UserFarmClubRepositoryImpl implements UserFarmClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserFarmClub findByUserIdAndFarmClubId(Long userId, Long farmClubId) {
        return queryFactory
                .select(userFarmClub)
                .from(userFarmClub)
                .where(userFarmClub.userId.eq(userId)
                        .and(userFarmClub.farmClub.id.eq(farmClubId)))
                .fetchOne();
    }
}
