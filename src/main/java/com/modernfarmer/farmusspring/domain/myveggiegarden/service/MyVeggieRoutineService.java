package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieRoutineService {

    private final MyVeggieGardenService myVeggieGardenService;


    @Transactional
    public BaseResponseDto<Void> settingMyVeggieRoutine(
            SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest
    )  {
        addMyyVeggieRoutine(settingMyVeggieRoutineReqeuest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }



    private void addMyyVeggieRoutine(SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(settingMyVeggieRoutineReqeuest.getMyVeggieId());
        Routine newRoutine = Routine.createRoutine(
                new Date(),
                settingMyVeggieRoutineReqeuest.getContent(),
                settingMyVeggieRoutineReqeuest.getPeriod(),
                myVeggie,
                settingMyVeggieRoutineReqeuest.isNotify()
        );
        myVeggie.addRoutine(newRoutine);
    }


}
