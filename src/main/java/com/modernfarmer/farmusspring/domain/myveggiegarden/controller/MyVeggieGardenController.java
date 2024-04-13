package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DeleteMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.SelectMyVeggieProfileResponse;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieGardenService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-veggie")
public class MyVeggieGardenController {

    private final MyVeggieGardenService myVeggieGardenService;

    @PostMapping(value = "")
    public BaseResponseDto<Void> settingMyVeggie(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody SettingMyVeggieRequest settingMyVeggi
            ){
        return  myVeggieGardenService.settingMyVeggie(
                user.getUserId(),
                settingMyVeggi
        );
    }

    @DeleteMapping(value = "")
    public BaseResponseDto<Void> deleteMyVeggie(
            @Validated @RequestBody DeleteMyVeggieRequest deleteMyVeggieRequest
            ){
        return  myVeggieGardenService.deleteMyVeggie(deleteMyVeggieRequest);
    }


    @GetMapping(value = "/simple-list")
    public BaseResponseDto<Void> selectMyVeggieList(
            @AuthenticationPrincipal CustomUser user
    ){
        return myVeggieGardenService.selectMyVeggieList(user.getUserId());
    }

    @GetMapping(value = "/{myVeggieId}/profile")
    public BaseResponseDto<SelectMyVeggieProfileResponse> selectMyVeggieProfile(
            @PathVariable("myVeggieId") Long myVeggieId
    ){
        return myVeggieGardenService.selectMyVeggieProfile(myVeggieId);
    }



}
