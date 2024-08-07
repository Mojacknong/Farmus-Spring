package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;

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
}
