package com.modernfarmer.farmusspring.domain.user.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.response.SetLevelResponse;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.service.UserService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto<Void> settingProfile(
            @AuthenticationPrincipal CustomUser user,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam("nickName") String nickName) throws IOException {

        return  userService.settingProfile(user.getUserId(), multipartFile,nickName);

    }

}
