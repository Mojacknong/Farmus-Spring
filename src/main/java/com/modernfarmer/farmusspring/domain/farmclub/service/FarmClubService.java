package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.req.RegisterFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.*;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForCreateFarmClub;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.service.VeggieInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmClubService {

    private final FarmClubRepository farmClubRepository;
    private final FarmClubHelper farmClubHelper;
    private final VeggieInfoService veggieInfoService;
    private final VeggieInfoHelper veggieInfoHelper;

    public CreateFarmClubResponseDto createFarmClub(CreateFarmClubRequestDto request) {
        // 몽고에서 이미지, 난이도 가져오기
        InfoForCreateFarmClub veggieInfo = veggieInfoService.getVeggieInfoForCreateFarmClub(request.veggieInfoId());
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

    public List<GetMyFarmClubListResponseDto> getMyFarmClubList(Long userId) {
        return farmClubRepository.findMyFarmClubList(userId);
    }

    private FarmClub createFarmClubEntity(CreateFarmClubRequestDto request, InfoForCreateFarmClub veggieInfo) {
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
