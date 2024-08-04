package com.modernfarmer.farmusspring.domain.farmclub.helper;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.repository.UserFarmClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFarmClubHelper {

    private final UserFarmClubRepository userFarmClubRepository;

    public UserFarmClub getUserFarmClubEntity(Long id) {
        return userFarmClubRepository.findById(id)
                .orElseThrow(() -> new FarmClubEntityNotFoundException("존재하지 않는 등록 정보입니다.", FarmClubErrorCode.USER_FARM_CLUB_NOT_FOUND));
    }

    public void deleteUserFarmClub(UserFarmClub userFarmClub) {
        userFarmClubRepository.delete(userFarmClub);
    }

    public UserFarmClub findByUserIdAndFarmClubId(Long userId, Long farmClubId) {
        return userFarmClubRepository.findByUserIdAndFarmClubId(userId, farmClubId);
    }
}
