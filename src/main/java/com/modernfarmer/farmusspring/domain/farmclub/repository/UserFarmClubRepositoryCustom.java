package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;

import java.util.List;

public interface UserFarmClubRepositoryCustom {

    UserFarmClub findByUserIdAndFarmClubId(Long userId, Long farmClubId);

    List<Long> findFarmClubIdsByUserId(Long userId);
}
