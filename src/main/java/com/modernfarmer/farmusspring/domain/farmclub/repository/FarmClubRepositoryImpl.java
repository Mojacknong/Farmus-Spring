package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.QGetMyFarmClubVo_BaseInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QMyVeggie.myVeggie;
import static com.modernfarmer.farmusspring.domain.user.entity.QUser.user;

public class FarmClubRepositoryImpl implements FarmClubRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SearchFarmClubResponseDto> findByConditions(List<String> difficulties, String keyword) {
        QFarmClub farmClub = QFarmClub.farmClub;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(new QSearchFarmClubResponseDto(farmClub))
                .from(farmClub)
                .where(farmClub.difficulty.in(difficulties))
                .where(farmClub.name.contains(keyword))
                .fetch();
    }

    @Override
    public List<GetMyFarmClubListResponseDto> findMyFarmClubList(Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(new QGetMyFarmClubListResponseDto(farmClub))
                .from(user)
                .join(user.myVeggies, myVeggie)
                .join(myVeggie.userFarmClub, userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(user.id.eq(userId))
                .fetch();
    }

    @Override
    public GetMyFarmClubVo findMyFarmClub(Long farmClubId, Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        GetMyFarmClubVo.BaseInfo baseInfo = queryFactory
                .select(new QGetMyFarmClubVo_BaseInfo(farmClub))
                .from(farmClub)
                .where(farmClub.id.eq(farmClubId))
                .fetchOne();

        if (baseInfo != null) {
            Long userFarmClubCount = queryFactory
                    .select(userFarmClub.count())
                    .from(userFarmClub)
                    .where(userFarmClub.farmClub.id.eq(farmClubId))
                    .fetchOne();

            Integer daySinceStart = queryFactory
                    .select(userFarmClub.createdDate.dayOfYear().subtract(farmClub.startedAt.dayOfYear()))
                    .from(userFarmClub)
                    .where(userFarmClub.farmClub.id.eq(farmClubId))
                    .where(userFarmClub.userId.eq(userId))
                    .fetchOne();

            return GetMyFarmClubVo.of(baseInfo, userFarmClubCount, daySinceStart);
        } else {
            throw new FarmClubNotFoundException("내 팜클럽을 불러오는 도중 에러가 발생했습니다.");
        }
    }

}
