package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.req.RegisterFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListWithStepCountsAndImages;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.CreateFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmClubService {

    private final FarmClubHelper farmClubHelper;
    private final VeggieInfoHelper veggieInfoHelper;

    private final MissionPostRepository missionPostRepository;
    private final FarmClubRepository farmClubRepository;

    public CreateFarmClubResponseDto createFarmClub(CreateFarmClubRequestDto request) {
        // 몽고에서 이미지, 난이도 가져오기
        CreateFarmClubVo veggieInfo = veggieInfoHelper.getVeggieInfoForCreateFarmClub(request.veggieInfoId());
        FarmClub farmClub = createFarmClubEntity(request, veggieInfo);
        Long newFarmClubId = farmClubRepository.save(farmClub).getId();
        // 채소 id로 팜클럽에 가입하는 메서드 추가

        return CreateFarmClubResponseDto.of(newFarmClubId);
    }

    public GetFarmClubResponseDto getFarmClub(Long id) {
        FarmClub farmClub = farmClubHelper.getFarmClubEntity(id);
        List<String> help = veggieInfoHelper.getVeggieInfoHelp(farmClub.getVeggieInfoId());
        return GetFarmClubResponseDto.of(farmClub, farmClub.getUserFarmClubs().size(), help);
    }

    public List<SearchFarmClubResponseDto> searchFarmClub(List<String> difficulties, String keyword) {
        return farmClubRepository.findByConditions(difficulties,  keyword);
    }

    @Transactional
    public RegisterFarmClubResponseDto registerFarmClub(RegisterFarmClubRequestDto request, Long userId) {
        // 채소 id로 채소 불러옴
        MyVeggie myVeggie = null;
        FarmClub farmClub = farmClubHelper.getFarmClubEntity(request.farmClubId());
        // 채소정보 id로 채소의 첫 스텝명 불러옴
        String stepName = "";
//        UserFarmClub userFarmClub = createUserFarmClubEntity(userId, stepName, farmClub, myVeggie);
//        farmClub.addUserFarmClub(userFarmClub);
        return null;
    }

    // 팜클럽으로부터 채소 정보 id, 이름, 이미지, 시작일, 전체 멤버 수 가져옴
    // 채소 정보로부터 랜덤 도움말 1, 스텝 정보(스텝수, 스텝명) 가져옴
    // 미션포스트로부터 각 스텝 별 완료 수, 이미지 3개 가져옴
    // 가져온 스텝 정보들을 조합해서 최종 GetMyFarmClubResponseDto 생성
    public GetMyFarmClubResponseDto getMyFarmClub(Long userId, Long farmClubId) {
        GetMyFarmClubVo farmClubInfo = farmClubRepository.findMyFarmClub(farmClubId, userId);
        String veggieInfoId = farmClubInfo.veggieInfoId();
        List<StepVo> stepList = veggieInfoHelper.getStepList(veggieInfoId);
        String randomTip = getRandomTip(stepList);
        List<GetMissionPostListWithStepCountsAndImages> missionList =
                missionPostRepository.getMissionPostStepNumAndImage(farmClubId);

        return GetMyFarmClubResponseDto.of(farmClubInfo, GetMyFarmClubResponseDto.createSteps(stepList, missionList), randomTip);
    }

    public List<GetMyFarmClubListResponseDto> getMyFarmClubList(Long userId) {
        return farmClubRepository.findMyFarmClubList(userId);
    }

    private String getRandomTip(List<StepVo> stepList) {
        List<String> tips = new ArrayList<>();
        stepList.forEach(step -> tips.addAll(step.tips()));
        return tips.get((int) (Math.random() * tips.size()));
    }

    private FarmClub createFarmClubEntity(CreateFarmClubRequestDto request, CreateFarmClubVo veggieInfo) {
        return FarmClub.createFarmClub(
                request.veggieInfoId(),
                request.farmClubName(),
                request.farmClubDescription(),
                veggieInfo.difficulty(),
                veggieInfo.veggieImage(),
                request.maxMemberCount(),
                LocalDate.now());
    }

    private UserFarmClub createUserFarmClubEntity(Long userId, String stepName, FarmClub farmClub, MyVeggie myVeggie) {
        return UserFarmClub.createUserFarmClub(userId, stepName, farmClub, myVeggie);
    }
}
