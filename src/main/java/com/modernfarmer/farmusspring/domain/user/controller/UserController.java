package com.modernfarmer.farmusspring.domain.user.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.response.SetLevelResponse;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.service.UserService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "")
    public BaseResponseDto<UserProfileResponse> selectUserProfile(@AuthenticationPrincipal CustomUser user)  {

        return userService.selectUserProfile(user.getUserId());
    }


    @PatchMapping(value = "/profile-image")
    public BaseResponseDto<Void> deleteProfleImage(@AuthenticationPrincipal CustomUser user)  {

        return userService.deleteProfleImage(user.getUserId());
    }

}
