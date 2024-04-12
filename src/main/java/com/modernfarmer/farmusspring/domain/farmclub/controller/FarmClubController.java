package com.modernfarmer.farmusspring.domain.farmclub.controller;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.helper.FarmClubHelper;
import com.modernfarmer.farmusspring.domain.farmclub.service.FarmClubService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm-club")
public class FarmClubController {

    private final FarmClubService farmClubService;
    private final FarmClubHelper farmClubHelper;

    // 요청 : 이름, 설명, 최대인원, 모집기한, 내 채소 id, 채소정보 id
    // 응답 : 팜클럽 id
    @PostMapping
    public BaseResponseDto<?> createFarmClub(
            @RequestBody CreateFarmClubRequestDto requestDto
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED, farmClubService.createFarmClub(requestDto));
    }

    @PostMapping("/register")
    public BaseResponseDto<?> registerFarmClub(
            @RequestBody CreateFarmClubRequestDto requestDto
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED, farmClubService.createFarmClub(requestDto));
    }

    @GetMapping("/search")
    public BaseResponseDto<?> searchFarmClub(
            @RequestParam List<String> difficulties,
            @RequestParam String keyword
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubHelper.searchFarmClub(difficulties, keyword));
    }

    @GetMapping("/{id}")
    public BaseResponseDto<?> getFarmClub(
            @PathVariable Long id
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getFarmClub(id));
    }
}
