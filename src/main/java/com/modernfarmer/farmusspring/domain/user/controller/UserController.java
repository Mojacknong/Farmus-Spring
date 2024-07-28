package com.modernfarmer.farmusspring.domain.user.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.AlarmUpdate;
import com.modernfarmer.farmusspring.domain.user.dto.response.AlarmStatus;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.service.UserService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PatchMapping("/notification")
    public BaseResponseDto<?> modifyNotification(@AuthenticationPrincipal CustomUser user, @Validated  @RequestBody  AlarmUpdate alarmUpdate)  {
        userService.modifyNotification(user.getUserId(), alarmUpdate.getStatus());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/notification")
    public BaseResponseDto<?> bringNotification(@AuthenticationPrincipal CustomUser user)  {
        AlarmStatus result = userService.bringNotification(user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }



    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponseDto<Void> settingProfile(
            @AuthenticationPrincipal CustomUser user,
            @RequestPart String nickname,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return  userService.settingProfile(user.getUserId(), file, nickname);
    }

    @PostMapping("/init/{userId}")
    public void initUser(
            @PathVariable Long userId
    ) {
        userService.initUser(userId);
    }




}
