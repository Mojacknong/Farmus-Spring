package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetMyFarmClubListResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;

import java.util.List;

public interface FarmClubRepositoryCustom {
    List<SearchFarmClubResponseDto> findByConditions(List<String> difficulties, String keyword);
    List<GetMyFarmClubListResponseDto> findMyFarmClubList(Long userId);
}
