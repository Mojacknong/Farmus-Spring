package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetMyFarmClubListResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.QGetMyFarmClubListResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.QSearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static com.modernfarmer.farmusspring.domain.farmclub.entity.QFarmClub.farmClub;
import static com.modernfarmer.farmusspring.domain.farmclub.entity.QUserFarmClub.userFarmClub;
import static com.modernfarmer.farmusspring.domain.myveggiegarden.entity.QMyVeggie.myVeggie;
import static com.modernfarmer.farmusspring.domain.user.entity.QUser.user;

public class FarmClubRepositoryImpl implements FarmClubRepositoryCustom{

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
}
