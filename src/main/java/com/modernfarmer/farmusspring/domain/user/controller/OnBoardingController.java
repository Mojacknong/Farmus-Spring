package com.modernfarmer.farmusspring.domain.user.controller;

import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetMotivationRequest;
import com.modernfarmer.farmusspring.domain.user.dto.response.EncouragementMessageDto;
import com.modernfarmer.farmusspring.domain.user.dto.response.SetLevelResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.service.OnBoardingService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/on-boarding")

public class OnBoardingController {

    private final OnBoardingService onBoardingService;


    @PostMapping(value = "/motivation")
    public BaseResponseDto<Void> settingMotivation(@AuthenticationPrincipal CustomUser user,
                                                @Validated @RequestBody SetMotivationRequest setMotivationRequest
    )  {
        return onBoardingService.settingMotivation(
                user.getUserId(),
                setMotivationRequest);
    }

    @PostMapping(value = "/level")
    public BaseResponseDto<SetLevelResponse> settingLevel(@AuthenticationPrincipal CustomUser user,
                                                      @Validated @RequestBody SetLevelRequest setLevelRequest
                                                      )  {

        return onBoardingService.settingLevel(user.getUserId(), setLevelRequest);
    }


    @PatchMapping (value = "/complete")
    public BaseResponseDto<Void> completeOnBoarding(@AuthenticationPrincipal CustomUser user)  {

        return onBoardingService.completeOnBoarding(user.getUserId());
    }

    @GetMapping("/encouragement-message")
    public BaseResponseDto<?> bringEncouragementMessage(@AuthenticationPrincipal CustomUser user) {
        EncouragementMessageDto result = onBoardingService.bringEncouragementMessage(user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }
}
