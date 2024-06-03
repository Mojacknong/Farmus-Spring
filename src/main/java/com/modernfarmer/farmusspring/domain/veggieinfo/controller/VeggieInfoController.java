package com.modernfarmer.farmusspring.domain.veggieinfo.controller;

import com.modernfarmer.farmusspring.domain.veggieinfo.service.VeggieInfoService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/veggie-info")
@Slf4j
public class VeggieInfoController {

    private final VeggieInfoService veggieInfoService;

    @GetMapping
    public BaseResponseDto<?> getVeggieInfoListForRegister() {
        return BaseResponseDto.of(SuccessCode.SUCCESS, veggieInfoService.getVeggieInfoListForRegister());
    }

    @GetMapping("/recommend")
    public BaseResponseDto<?> getRecommendVeggieList(
            @RequestParam String difficulty) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, veggieInfoService.getRecommendVeggieList(difficulty));
    }
}
