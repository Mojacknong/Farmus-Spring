package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.QGetMyFarmClubVo_BaseInfo;
import com.modernfarmer.farmusspring.domain.history.vo.HistoryDetailVo;
import com.modernfarmer.farmusspring.domain.history.vo.QHistoryDetailVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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

        // 난이도 조건 설정
        BooleanExpression difficultyCondition = difficulties == null ? null : farmClub.difficulty.in(difficulties);
        BooleanExpression keywordCondition = keyword == null ? null : farmClub.name.contains(keyword);

        return queryFactory
                .select(new QSearchFarmClubResponseDto(
                        farmClub,
                        JPAExpressions
                                .select(userFarmClub.count())
                                .from(userFarmClub)
                                .where(userFarmClub.farmClub.eq(farmClub))
                ))
                .from(farmClub)
                .where(difficultyCondition)
                .where(keywordCondition)
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
                .where(userFarmClub.userId.eq(userId).and(userFarmClub.farmClub.id.eq(farmClubId)))
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

    @Override
    public List<FarmClub> getRecommendedFarmClubList(String level) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        // 그룹별로 veggieName을 기준으로 각 그룹에서 랜덤 한 개씩 선택
        List<FarmClub> result = queryFactory
                .selectFrom(farmClub)
                .where(farmClub.difficulty.eq(level))
                .orderBy(farmClub.veggieName.asc(), farmClub.id.asc())
                .fetch();

        // 각 veggieName 그룹에서 랜덤으로 하나씩 선택
        Map<String, List<FarmClub>> groupedByVeggieName = result.stream()
                .collect(Collectors.groupingBy(FarmClub::getVeggieName));

        Random random = new Random();
        return groupedByVeggieName.values().stream()
                .map(list -> list.get(random.nextInt(list.size())))
                .collect(Collectors.toList());
    }

}
