package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.vo.SuccessFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;

import java.util.List;
import java.util.Optional;

public interface UserFarmClubRepositoryCustom {

    Optional<UserFarmClub> findByUserIdAndFarmClubId(Long userId, Long farmClubId);

    List<Long> findFarmClubIdsByUserId(Long userId);
    SuccessFarmClubVo getFarmClubRecord(Long userId, Long farmClubId);

    void deleteFarmClubDomain(List<MyVeggie> myVeggieList);

    Optional<UserFarmClub> findFarmClubByMyVeggieId(Long myVeggieId);
}
