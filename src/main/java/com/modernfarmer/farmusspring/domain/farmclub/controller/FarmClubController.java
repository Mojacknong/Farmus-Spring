package com.modernfarmer.farmusspring.domain.farmclub.controller;

import com.modernfarmer.farmusspring.domain.farmclub.dto.req.CreateFarmClubRequestDto;
import com.modernfarmer.farmusspring.domain.farmclub.service.FarmClubService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm-club")
public class FarmClubController {

    private final FarmClubService farmClubService;

    // 요청 : 이름, 설명, 최대인원, 모집기한, 내 채소 id, 채소정보 id
    // 응답 : 팜클럽 id
    @PostMapping
    public BaseResponseDto<?> createFarmClub(
            @RequestBody CreateFarmClubRequestDto requestDto
    ) {


        return null;
    }

    @PostMapping("/register")
    public BaseResponseDto<?> registerFarmClub(
            @RequestBody CreateFarmClubRequestDto requestDto
    ) {

        return null;
    }

    @GetMapping("/search")
    public BaseResponseDto<?> searchFarmClub(
            @RequestParam String keyword
    ) {

        return null;
    }

    @GetMapping("/{id}")
    public BaseResponseDto<?> getFarmClub(
            @PathVariable Long id
    ) {

        return null;
    }
}
