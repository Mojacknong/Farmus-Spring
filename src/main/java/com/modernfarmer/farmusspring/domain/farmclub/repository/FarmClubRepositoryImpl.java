package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.joda.time.LocalDate;

import java.util.List;

public class FarmClubRepositoryImpl implements FarmClubRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SearchFarmClubResponseDto> findByConditions(List<String> difficulties, boolean isBefore, boolean isAfter, String keyword) {
        QFarmClub farmClub = QFarmClub.farmClub;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(Projections.constructor(SearchFarmClubResponseDto.class,
                                farmClub.id,
                                farmClub.name,
                                farmClub.description,
                                farmClub.veggieImage,
                                farmClub.difficulty,
                                farmClub.startedAt))
                .from(farmClub)
                .where(farmClub.difficulty.in(difficulties)
                        .and(isBefore ? farmClub.startedAt.after(LocalDate.now()) : null)
                        .and(isAfter ? farmClub.startedAt.before(LocalDate.now()) : null))
                .fetch();
    }
}
