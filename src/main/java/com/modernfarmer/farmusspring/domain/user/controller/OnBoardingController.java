package com.modernfarmer.farmusspring.domain.user.controller;

import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetMotivationRequest;
import com.modernfarmer.farmusspring.domain.user.dto.response.SetLevelResponse;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.service.OnBoardingService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")

public class OnBoardingController {

    private final OnBoardingService onBoardingService;


    @PostMapping(value = "/on-boarding/motivation")
    public BaseResponseDto<Void> settingMotiavation(@AuthenticationPrincipal CustomUser user,
                                                @Validated @RequestBody SetMotivationRequest setMotivationRequest
    )  {

        User aa = User.createUser("USER","2678968131",true);

        User bb = User.createUserObject(user.getUserId());


        return onBoardingService.settingMotiavation(
                aa,
                setMotivationRequest);
    }

    @PostMapping(value = "/on-boarding/level")
    public BaseResponseDto<SetLevelResponse> settingLevel(@AuthenticationPrincipal CustomUser user,
                                                      @Validated @RequestBody SetLevelRequest setLevelRequest
                                                      )  {

        return onBoardingService.settingLevel(user.getUserId(), setLevelRequest);
    }


    @PatchMapping (value = "/on-boarding/complete")
    public BaseResponseDto<Void> completeOnBoarding(@AuthenticationPrincipal CustomUser user)  {

        return onBoardingService.completeOnBoarding(user.getUserId());
    }
}
