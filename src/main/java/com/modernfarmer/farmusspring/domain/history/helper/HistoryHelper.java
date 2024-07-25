package com.modernfarmer.farmusspring.domain.history.helper;

import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.helper.MissionPostHelper;
import com.modernfarmer.farmusspring.domain.history.document.History;
import com.modernfarmer.farmusspring.domain.history.document.HistoryFarmClubDetail;
import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import com.modernfarmer.farmusspring.domain.history.exception.HistoryErrorCode;
import com.modernfarmer.farmusspring.domain.history.exception.custom.HistoryEntityNotFoundException;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryFarmClubDetailRepository;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryRepository;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryVeggieDetailRepository;
import com.modernfarmer.farmusspring.domain.history.vo.HistoryDetailVo;
import com.modernfarmer.farmusspring.domain.history.vo.MissionPostHistoryVo;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.MyVeggieHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HistoryHelper {

    private final HistoryRepository historyRepository;
    private final HistoryFarmClubDetailRepository historyFarmClubDetailRepository;
    private final HistoryVeggieDetailRepository historyVeggieDetailRepository;

    private final MissionPostHelper missionPostHelper;
    private final VeggieInfoHelper veggieInfoHelper;
    private final FarmClubHelper farmClubHelper;
    private final MyVeggieHelper myVeggieHelper;

    public void createUserHistory(Long userId) {
        History history = History.createHistory(userId);
        historyRepository.save(history);
    }

    public void createFarmClubHistoryDetail(Long userId, Long userFarmClubId, String veggieInfoId) {
        List<MissionPostHistoryVo> missionPostHistoryList = missionPostHelper.getMissionPostHistory(userFarmClubId);
        List<StepVo> stepList = veggieInfoHelper.getStepList(veggieInfoId);
        List<HistoryFarmClubDetail.HistoryClubPost> historyClubPostList = getHistoryClubPostList(missionPostHistoryList, stepList);
        HistoryFarmClubDetail historyFarmClubDetail = HistoryFarmClubDetail.createHistoryClubDetail(historyClubPostList);
        ObjectId farmClubDetailId = historyFarmClubDetailRepository.save(historyFarmClubDetail).getId();
        HistoryDetailVo historyDetailVo = farmClubHelper.getFarmClubDetail(userFarmClubId);
        History.Detail historyDetail = History.Detail.createDetail(
                farmClubDetailId.toHexString(),
                historyDetailVo.image(),
                historyDetailVo.historyName(),
                historyDetailVo.name(),
                historyDetailVo.period() + " - " + LocalDate.now());
        History history = getUserHistory(userId);
        history.getFarmClubHistoryDetails().add(historyDetail);
        historyRepository.save(history);
    }

    @Transactional
    public void createVeggieHistoryDetail(Long userId, Long myVeggieId) {
        MyVeggie myVeggie = myVeggieHelper.getMyVeggieEntity(myVeggieId);
        // 해당 채소의 모든 성장일기를 가져옴
        // 이미지, 내용, 날짜
        List<Diary> diaries = myVeggieHelper.getDiariesByMyVeggie(myVeggie);
        List<HistoryVeggieDetail.HistoryPost> diaryHistories = diaries.stream()
                .map(diary -> HistoryVeggieDetail.HistoryPost.builder()
                        .postImage(diary.getImage())
                        .content(diary.getContent())
                        .dateTime(DateManager.parsingDotDateTime(diary.getCreatedDate()))
                        .build())
                .toList();

        HistoryVeggieDetail historyVeggieDetail = HistoryVeggieDetail.createHistoryDetail(diaryHistories, null);
        String veggieDetailId = historyVeggieDetailRepository.save(historyVeggieDetail).getId().toHexString();

        History.Detail historyDetail = History.Detail.createDetail(
                veggieDetailId,
                myVeggie.getVeggieImage(),
                myVeggie.getNickname(),
                myVeggie.getVeggieName(),
                DateManager.parsingDotDate(myVeggie.getBirth()) + " - " + DateManager.parsingDotDateTime(LocalDateTime.now()));

        History history = getUserHistory(userId);
        history.getVeggieHistoryDetails().add(historyDetail);
        historyRepository.save(history);
    }

    private static List<HistoryFarmClubDetail.HistoryClubPost> getHistoryClubPostList(List<MissionPostHistoryVo> missionPostHistoryList, List<StepVo> stepList) {
        return missionPostHistoryList.stream()
                .map(missionPostHistoryVo -> {
                    StepVo step = stepList.stream()
                            .filter(stepVo -> stepVo.num() == missionPostHistoryVo.stepNum())
                            .findFirst()
                            .orElseThrow(() -> new HistoryEntityNotFoundException("해당 스텝이 존재하지 않습니다.", HistoryErrorCode.ENTITY_NOT_FOUND));

                    return HistoryFarmClubDetail.HistoryClubPost.builder()
                            .postImage(missionPostHistoryVo.image())
                            .content(missionPostHistoryVo.content())
                            .date(missionPostHistoryVo.date())
                            .stepNum(step.num())
                            .stepName(step.content())
                            .build();
                })
                .toList();
    }

    public History getUserHistory(Long userId) {
        return historyRepository.findByUserId(userId)
                .orElseThrow(() -> new HistoryEntityNotFoundException("해당 유저의 히스토리가 존재하지 않습니다.", HistoryErrorCode.ENTITY_NOT_FOUND));
    }

    public HistoryFarmClubDetail getFarmClubHistoryDetail(String farmClubDetailId) {
        return historyFarmClubDetailRepository.findById(new ObjectId(farmClubDetailId))
                .orElseThrow(() -> new HistoryEntityNotFoundException("해당 팜클럽 히스토리가 존재하지 않습니다.", HistoryErrorCode.ENTITY_NOT_FOUND));
    }

    public HistoryVeggieDetail getVeggieHistoryDetail(String veggieDetailId) {
        return historyVeggieDetailRepository.findById(new ObjectId(veggieDetailId))
                .orElseThrow(() -> new HistoryEntityNotFoundException("해당 채소 히스토리가 존재하지 않습니다.", HistoryErrorCode.ENTITY_NOT_FOUND));
    }
}
