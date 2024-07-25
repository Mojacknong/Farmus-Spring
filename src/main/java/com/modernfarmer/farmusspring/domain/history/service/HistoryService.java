package com.modernfarmer.farmusspring.domain.history.service;

import com.modernfarmer.farmusspring.domain.history.document.History;
import com.modernfarmer.farmusspring.domain.history.document.HistoryFarmClubDetail;
import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import com.modernfarmer.farmusspring.domain.history.dto.res.*;
import com.modernfarmer.farmusspring.domain.history.helper.HistoryHelper;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryHelper historyHelper;

    public HistoryResponseDto getUserHistory(Long userId) {
        return null;
    }

    public VeggieHistoryDetailResponseDto getVeggieHistoryDetail(ObjectId detailId) {
        HistoryVeggieDetail historyVeggieDetail = historyHelper.getVeggieHistoryDetail(detailId);
        return VeggieHistoryDetailResponseDto.of(historyVeggieDetail.getDiaryPosts(), historyVeggieDetail.getFarmResult());
    }

    public VeggieHistoryListResponseDto getVeggieHistories(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return VeggieHistoryListResponseDto.of(history.getVeggieHistoryDetails());
    }

    public FarmClubHistoryDetailResponseDto getFarmClubHistoryDetail(ObjectId detailId) {
        HistoryFarmClubDetail historyFarmClubDetail = historyHelper.getFarmClubHistoryDetail(detailId);
        return FarmClubHistoryDetailResponseDto.of(historyFarmClubDetail.getMissionPostList());
    }

    public FarmClubHistoryListResponseDto getFarmClubHistories(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return FarmClubHistoryListResponseDto.of(history.getFarmClubHistoryDetails());
    }
}
