package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggiDiaryRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggiRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieGardenService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
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
@RequestMapping("/api/my-veggie")
public class MyVeggieGardenController {

    private final MyVeggieGardenService myVeggieGardenService;

    @PostMapping(value = "/")
    public BaseResponseDto<Void> settingMyVeggi(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody SettingMyVeggiRequest settingMyVeggi
            ){

        return  myVeggieGardenService.settingMyVeggi(
                user.getUserId(),
                settingMyVeggi
        );

    }




}
