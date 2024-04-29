package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieRoutineService {

    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;



    @Transactional
    public BaseResponseDto<Void> settingMyVeggieRoutine(
            SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest
    )  {
        addMyyVeggieRoutine(settingMyVeggieRoutineReqeuest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }


    @Transactional
    public List<MyVeggieRoutine> selectMyVeggieRoutineById(MyVeggie myVeggie) {

        List<Routine> routineList = myVeggieRepository.findMyVeggieRoutineById(myVeggie);
        return new MyVeggieRoutine().processData(routineList);
    }

    @Transactional
    public List<MyRoutineList> selectMyVeggieRoutine(Long userId) {

        List<MyVeggie> myVeggieList = myVeggieRepository.findMyVeggieAndRoutine(userId);
        return new MyRoutineList().processData(myVeggieList);
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
