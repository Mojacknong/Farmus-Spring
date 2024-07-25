package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineCheck;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineSetting;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieRoutineService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-veggie/routine")
public class MyVeggieRoutineController {

    private final MyVeggieRoutineService myVeggieRoutineService;

    @PostMapping()
    public BaseResponseDto<?> settingVeggieRoutine(@Validated @RequestBody RoutineSetting settingRoutine) {
        myVeggieRoutineService.settingVeggieRoutine(settingRoutine.getMyVeggieId(), settingRoutine.getContent(), settingRoutine.getPeriod());
        log.info("루틴 추가 완료");
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }


    @PatchMapping()
    public BaseResponseDto<?> modifyRoutine(@Validated @RequestBody RoutineUpdate routineUpdate) {
        myVeggieRoutineService.modifyRoutine(routineUpdate);
        log.info("루틴 수정 완료");
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @DeleteMapping
    public BaseResponseDto<?> eraseRoutine(@Validated @RequestBody RoutineDelete routineDelete) {
        myVeggieRoutineService.eraseRoutine(routineDelete);
        log.info("루틴 삭제 완료");
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }


    @PostMapping(value = "/check")
    public BaseResponseDto<?> checkVeggieRoutine(@Validated @RequestBody RoutineCheck routineCheck) {
        myVeggieRoutineService.checkVeggieRoutine(routineCheck.getRoutineId());
        log.info("루틴 체킹 완료");
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

//    @GetMapping(value = "/list")
//    public BaseResponseDto<?> selectMyVeggieRoutine(
//            @AuthenticationPrincipal CustomUser user
//    ) {
//        List<MyRoutineList> result = myVeggieRoutineService.selectMyVeggieRoutine(user.getUserId());
//        log.info("채소별 리스트 조회 종료");
//        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
//    }
}
