package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetMyFarmClubListResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetRecommendFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetFarmClubUserVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.history.vo.HistoryDetailVo;

import java.util.List;

public interface FarmClubRepositoryCustom {
    List<SearchFarmClubResponseDto> findByConditions(List<String> difficulties, String keyword);
    List<GetMyFarmClubListResponseDto> findMyFarmClubList(Long userId);
    GetMyFarmClubVo findMyFarmClub(Long farmClubId, Long userId);
    HistoryDetailVo getFarmClubDetail(Long userFarmClubId);
    List<GetFarmClubUserVo> findFarmClubUserList(Long userId, Long farmClubId);
    List<FarmClub> getRecommendedFarmClubList(String level);
}
