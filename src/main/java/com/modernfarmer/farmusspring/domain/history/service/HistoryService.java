package com.modernfarmer.farmusspring.domain.history.service;

import com.modernfarmer.farmusspring.domain.history.document.History;
import com.modernfarmer.farmusspring.domain.history.document.HistoryFarmClubDetail;
import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import com.modernfarmer.farmusspring.domain.history.dto.req.VeggieHistoryResultPostRequestDto;
import com.modernfarmer.farmusspring.domain.history.dto.res.*;
import com.modernfarmer.farmusspring.domain.history.helper.HistoryHelper;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryHelper historyHelper;
    private final S3Service s3Service;

    public void createHistory(Long userId) {
        historyHelper.createUserHistory(userId);
    }

    public FarmClubHistoryIconResponseDto getFarmClubHistoryIcons(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return FarmClubHistoryIconResponseDto.of(history);
    }

    public VeggieHistoryIconResponseDto getVeggieHistoryIcons(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return VeggieHistoryIconResponseDto.of(history);
    }

    public VeggieHistoryDetailResponseDto getVeggieHistoryDetail(String detailId) {
        HistoryVeggieDetail historyVeggieDetail = historyHelper.getVeggieHistoryDetail(detailId);
        return VeggieHistoryDetailResponseDto.of(historyVeggieDetail.getDiaryPosts(), historyVeggieDetail.getFarmResult());
    }

    public VeggieHistoryListResponseDto getVeggieHistories(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return VeggieHistoryListResponseDto.of(history.getVeggieHistoryDetails());
    }

    public FarmClubHistoryDetailResponseDto getFarmClubHistoryDetail(String detailId) {
        HistoryFarmClubDetail historyFarmClubDetail = historyHelper.getFarmClubHistoryDetail(detailId);
        return FarmClubHistoryDetailResponseDto.of(historyFarmClubDetail.getMissionPostList());
    }

    public FarmClubHistoryListResponseDto getFarmClubHistories(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return FarmClubHistoryListResponseDto.of(history.getFarmClubHistoryDetails());
    }

    public void createVeggieHistoryResult(VeggieHistoryResultPostRequestDto requestDto, MultipartFile image) {
        String imageUrl = s3Service.uploadImage(image, "farm-result");
        HistoryVeggieDetail.HistoryPost farmResult = HistoryVeggieDetail.HistoryPost.builder()
                .postImage(imageUrl)
                .content(requestDto.content())
                .dateTime(DateManager.parsingDotDateTime(LocalDateTime.now()))
                .build();
        historyHelper.createVeggieHistoryResult(farmResult, requestDto.historyDetailId());
    }
}
