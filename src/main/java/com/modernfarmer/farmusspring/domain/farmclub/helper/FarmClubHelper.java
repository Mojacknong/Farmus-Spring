package com.modernfarmer.farmusspring.domain.farmclub.helper;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.exception.FarmClubErrorCode;
import com.modernfarmer.farmusspring.domain.farmclub.exception.custom.FarmClubEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import com.modernfarmer.farmusspring.domain.history.vo.HistoryDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FarmClubHelper {

    private final FarmClubRepository farmClubRepository;

    public FarmClub getFarmClubEntity(Long id) {
        return farmClubRepository.findById(id).orElseThrow(() ->
                new FarmClubEntityNotFoundException("존재하지 않는 팜클럽입니다.", FarmClubErrorCode.FARM_CLUB_NOT_FOUND));
    }

    public HistoryDetailVo getFarmClubDetail(Long userFarmClubId) {
        return farmClubRepository.getFarmClubDetail(userFarmClubId);
    }
}
