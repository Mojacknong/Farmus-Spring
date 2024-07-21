package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import com.modernfarmer.farmusspring.domain.farmclub.repository.MissionPostRepository;
import com.modernfarmer.farmusspring.domain.farmclub.repository.UserFarmClubRepository;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMissionPostListVo;
import com.modernfarmer.farmusspring.domain.farmclub.vo.GetMyFarmClubVo;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.MyVeggieHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo;
import com.modernfarmer.farmusspring.domain.user.helper.UserHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.VeggieInfoVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmClubService {
    private final UserFarmClubRepository userFarmClubRepository;

    private final FarmClubHelper farmClubHelper;
    private final VeggieInfoHelper veggieInfoHelper;
    private final MyVeggieHelper myVeggieHelper;
    private final UserHelper userHelper;

    private final MissionPostRepository missionPostRepository;
    private final FarmClubRepository farmClubRepository;

    @Transactional
    public CreateFarmClubResponseDto createFarmClub(CreateFarmClubRequestDto request, Long userId) {
        // 몽고에서 이미지, 난이도 가져오기
        VeggieInfoVo veggieInfo = veggieInfoHelper.getVeggieInfo(request.veggieInfoId());
        FarmClub farmClub = createFarmClubEntity(request, veggieInfo);
        Long newFarmClubId = farmClubRepository.save(farmClub).getId();
        // 채소 id로 팜클럽에 가입하는 메서드 추가
        registerFarmClub(newFarmClubId, request.myVeggieId(), userId);

        return CreateFarmClubResponseDto.of(newFarmClubId);
    }

    public GetFarmClubResponseDto getFarmClub(Long id) {
        FarmClub farmClub = farmClubHelper.getFarmClubEntity(id);
        VeggieInfo.Help help = veggieInfoHelper.getVeggieInfoHelp(farmClub.getVeggieInfoId());
        return GetFarmClubResponseDto.of(farmClub, farmClub.getUserFarmClubs().size(), help);
    }

    public GetRecommendFarmClubResponseDto getRecommendedFarmClubList(Long userId) {
        String level = userHelper.getUserLevel(userId);
        List<FarmClub> result = farmClubRepository.getRecommendedFarmClubList(level);
        VeggieInfo.Help helpFirst = veggieInfoHelper.getVeggieInfoHelp(result.get(0).getVeggieInfoId());
        VeggieInfo.Help helpSecond = veggieInfoHelper.getVeggieInfoHelp(result.get(1).getVeggieInfoId());
        return GetRecommendFarmClubResponseDto.of(
                GetFarmClubResponseDto.of(result.get(0), result.get(0).getUserFarmClubs().size(), helpFirst),
                GetFarmClubResponseDto.of(result.get(1), result.get(1).getUserFarmClubs().size(), helpSecond)
        );
    }

    public List<SearchFarmClubResponseDto> searchFarmClub(List<String> difficulties, String keyword, Long userId) {
        List<Long> farmClubIds = farmClubHelper.findFarmClubIdsByUserId(userId);
        List<SearchFarmClubResponseDto> farmClubs = farmClubRepository.findByConditions(difficulties, keyword);
        return farmClubs.stream()
                .filter(farmClub -> !farmClubIds.contains(farmClub.id()))
                .filter(farmClub -> farmClub.maxUser() > farmClub.curUser())
                .filter(farmClub -> farmClub.startedAt().compareTo(LocalDate.now().toString()) >= 0)
                .toList();
    }

    @Transactional
    public RegisterFarmClubResponseDto registerFarmClub(Long farmClubId, Long myVeggieId, Long userId) {
        // 채소 id로 채소 불러옴
        MyVeggie myVeggie = myVeggieHelper.getMyVeggieEntity(myVeggieId);
        FarmClub farmClub = farmClubHelper.getFarmClubEntity(farmClubId);
        // 채소정보 id로 채소의 첫 스텝명 불러옴
        String stepName = veggieInfoHelper.getStepName(farmClub.getVeggieInfoId(), 1);
        UserFarmClub userFarmClub = createUserFarmClubEntity(userId, stepName, farmClub, myVeggie);
        farmClub.addUserFarmClub(userFarmClub);
        myVeggie.setUserFarmClub(userFarmClub);
        return RegisterFarmClubResponseDto.of(userFarmClub.getId());
    }

    // 팜클럽으로부터 채소 정보 id, 이름, 이미지, 시작일, 전체 멤버 수 가져옴
    // 채소 정보로부터 랜덤 도움말 1, 스텝 정보(스텝수, 스텝명) 가져옴
    // 미션포스트로부터 각 스텝 별 완료 수, 이미지 3개 가져옴
    // 가져온 스텝 정보들을 조합해서 최종 GetMyFarmClubResponseDto 생성
    public GetMyFarmClubResponseDto getMyFarmClub(Long farmClubId, Long userId) {
        GetMyFarmClubVo farmClubInfo = farmClubRepository.findMyFarmClub(farmClubId, userId);
        String veggieInfoId = farmClubInfo.veggieInfoId();
        List<StepVo> stepList = veggieInfoHelper.getStepList(veggieInfoId);
        String randomTip = getRandomTip(stepList);
        List<GetMissionPostListVo> missionList =
                missionPostRepository.getMissionPostStepNumAndImage(farmClubId);

        log.info("missionList: {}", missionList);

        return GetMyFarmClubResponseDto.of(farmClubInfo, GetMyFarmClubResponseDto.createSteps(stepList, missionList), randomTip);
    }

    public List<GetMyFarmClubListResponseDto> getMyFarmClubList(Long userId) {
        return farmClubRepository.findMyFarmClubList(userId);
    }

    public GetMyVeggieResponseDto getMyVeggieForRegister(Long userId, String veggieInfoId) {
        MyVeggieVo myVeggie = myVeggieHelper.getMyVeggieInfo(userId, veggieInfoId);
        return GetMyVeggieResponseDto.of(myVeggie.myVeggieId(), myVeggie.nickname());
    }

    public List<MyVeggieVo> getMyVeggieForCreate(Long userId) {
        return myVeggieHelper.getMyVeggieInfo(userId);
    }

    public GetHelpAllResponseDto getHelpAll(Long farmClubId) {
        String veggieInfoId = farmClubHelper.getFarmClubEntity(farmClubId).getVeggieInfoId();
        VeggieInfo veggieInfoEntity = veggieInfoHelper.getVeggieInfoEntity(veggieInfoId);
        return GetHelpAllResponseDto.of(veggieInfoEntity);
    }

    // 팜클럽 탈퇴
    public void withdrawFarmClub(Long farmClubId, Long userId, Boolean deleteVeggie) {
        UserFarmClub userFarmClub = userFarmClubRepository.findByUserIdAndFarmClubId(userId, farmClubId);
        userFarmClubRepository.deleteById(userFarmClub.getId());
        if (deleteVeggie) {
            Long myVeggieId = userFarmClub.getMyVeggie().getId();
            myVeggieHelper.getMyVeggieEntity(myVeggieId);
            myVeggieHelper.deleteMyVeggie(myVeggieId);
        }
    }

    private String getRandomTip(List<StepVo> stepList) {
        List<String> tips = new ArrayList<>();
        stepList.forEach(step -> tips.addAll(step.tips()));

        if (tips.isEmpty()) {
            return "아직 도움말이 없습니다.";
        }

        return tips.get((int) (Math.random() * tips.size()));
    }

    private FarmClub createFarmClubEntity(CreateFarmClubRequestDto request, VeggieInfoVo veggieInfo) {
        return FarmClub.createFarmClub(
                request.veggieInfoId(),
                request.farmClubName(),
                request.farmClubDescription(),
                veggieInfo.difficulty(),
                veggieInfo.veggieImage(),
                veggieInfo.name(),
                request.maxMemberCount(),
                LocalDate.now()
        );
    }

    private UserFarmClub createUserFarmClubEntity(Long userId, String stepName, FarmClub farmClub, MyVeggie myVeggie) {
        return UserFarmClub.createUserFarmClub(userId, stepName, farmClub, myVeggie);
    }
}
