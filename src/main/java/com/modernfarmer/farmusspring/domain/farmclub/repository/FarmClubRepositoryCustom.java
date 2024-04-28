package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetMyFarmClubListResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;

import java.util.List;

public interface FarmClubRepositoryCustom {
    List<SearchFarmClubResponseDto> findByConditions(List<String> difficulties, String keyword);
    List<GetMyFarmClubListResponseDto> findMyFarmClubList(Long userId);
    GetMyFarmClubVo findMyFarmClub(Long farmClubId, Long userId);
}
