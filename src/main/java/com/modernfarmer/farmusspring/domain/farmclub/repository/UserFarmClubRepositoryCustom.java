package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;

public interface UserFarmClubRepositoryCustom {

    UserFarmClub findByUserIdAndFarmClubId(Long userId, Long farmClubId);
}
