package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.MyRoutineCheck;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieRoutineService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-veggie/routine")
public class MyVeggieRoutineController {

    private final MyVeggieRoutineService myVeggieRoutineService;

    @PostMapping(value = "")
    public BaseResponseDto<Void> settingMyVeggieRoutine(
            @Validated @RequestBody SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest
            ) {
        return myVeggieRoutineService.settingMyVeggieRoutine(settingMyVeggieRoutineReqeuest);
    }

    @PostMapping(value = "/check")
    public BaseResponseDto<?> checkMyVeggieRoutine(
            @Validated @RequestBody MyRoutineCheck myRoutineCheck
    ) {
        myVeggieRoutineService.checkMyVeggieRoutine(myRoutineCheck.getRoutineId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping(value = "/{myVeggieId}")
    public BaseResponseDto<?> selectMyVeggieRoutineById(
            @PathVariable("myVeggieId") Long myVeggieId
    ) {
        MyVeggie myVeggie = MyVeggie.builder().id(myVeggieId).build();
        List<MyVeggieRoutine> result = myVeggieRoutineService.selectMyVeggieRoutineById(myVeggie);

        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }

    @GetMapping(value = "/list")
    public BaseResponseDto<?> selectMyVeggieRoutine(
            @AuthenticationPrincipal CustomUser user
    ) {

        List<MyRoutineList> result = myVeggieRoutineService.selectMyVeggieRoutine(user.getUserId());
        log.info("채소별 리스트 조회 종료");
        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }
}
