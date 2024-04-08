package com.modernfarmer.farmusspring.domain.farmclub.service;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.CreateFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.GetFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.dto.res.SearchFarmClubResponseDto;
import com.modernfarmer.farmusspring.domain.farmclub.entity.FarmClub;
import com.modernfarmer.farmusspring.domain.farmclub.repository.FarmClubRepository;
import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForCreateFarmClub;
import com.modernfarmer.farmusspring.domain.veggieinfo.service.VeggieInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmClubService {

    private final FarmClubRepository farmClubRepository;
    private final VeggieInfoService veggieInfoService;

    public CreateFarmClubResponseDto createFarmClub(CreateFarmClubRequestDto request) {
        // 몽고에서 이미지, 난이도 가져오기
        InfoForCreateFarmClub veggieInfo = veggieInfoService.getVeggieInfoForCreateFarmClub(request.veggieInfoId());
        FarmClub farmClub = createFarmClubEntity(request, veggieInfo);
        Long newFarmClubId = farmClubRepository.save(farmClub).getId();
        // 채소 id로 팜클럽에 가입하는 메서드 추가

        return CreateFarmClubResponseDto.of(newFarmClubId);
    }

    public GetFarmClubResponseDto getFarmClub(Long id) {
        FarmClub farmClub = getFarmClubEntity(id);

        return null;
    }

    public List<SearchFarmClubResponseDto> searchFarmClub(List<String> difficulties, String keyword) {
        return farmClubRepository.findByConditions(difficulties,  keyword);
    }

    private FarmClub getFarmClubEntity(Long id) {
        return farmClubRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팜클럽입니다."));
    }

    private FarmClub createFarmClubEntity(CreateFarmClubRequestDto request, InfoForCreateFarmClub veggieInfo) {
        return FarmClub.builder()
                .name(request.farmClubName())
                .description(request.farmClubDescription())
                .maxUser(request.maxMemberCount())
                .startedAt(LocalDate.parse(request.startDate()))
                .veggieInfoId(request.veggieInfoId())
                .veggieName(veggieInfo.name())
                .difficulty(veggieInfo.difficulty().name())
                .veggieImage(veggieInfo.veggieImage())
                .build();
    }
}
