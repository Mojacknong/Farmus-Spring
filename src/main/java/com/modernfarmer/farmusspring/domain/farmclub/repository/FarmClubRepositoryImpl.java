package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.QGetMyFarmClubVo_BaseInfo;
import com.modernfarmer.farmusspring.domain.history.vo.HistoryDetailVo;
import com.modernfarmer.farmusspring.domain.history.vo.QHistoryDetailVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QMyVeggie.myVeggie;
import static com.modernfarmer.farmusspring.domain.user.entity.QUser.user;

@Slf4j
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
                .from(userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(userFarmClub.userId.eq(userId))
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

        Long userFarmClubCount = queryFactory
                .select(userFarmClub.count())
                .from(userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .fetchOne();

        LocalDate userFarmClubCreatedDate = queryFactory
                .select(farmClub.startedAt)
                .from(userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(userFarmClub.userId.eq(userId))
                .fetchOne();

        log.info("userFarmClubCount: {}", userFarmClubCount);
        log.info("userFarmClubCreatedDate: {}", userFarmClubCreatedDate);

        return GetMyFarmClubVo.of(baseInfo, userFarmClubCount, userFarmClubCreatedDate);
    }

    public HistoryDetailVo getFarmClubDetail(Long userFarmClubId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(new QHistoryDetailVo(
                        farmClub.veggieImage,
                        farmClub.name,
                        farmClub.veggieName,
                        farmClub.startedAt.stringValue()
                ))
                .from(userFarmClub)
                .join(userFarmClub.farmClub, farmClub)
                .where(userFarmClub.id.eq(userFarmClubId))
                .fetchOne();
    }

}
