package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieRoutineService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
}
