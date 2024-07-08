package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.RoutineNotFountException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieRoutineService {

    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;
    private final DateManager dateManager;



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
        return  MyVeggieRoutine.processData(routineList);
    }

    @Transactional
    public List<MyRoutineList> selectMyVeggieRoutine(Long userId) {
        log.info("채소별 리스트 서비스 시작");
        List<MyVeggie> myVeggieList = myVeggieRepository.findMyVeggieAndRoutine(userId);
        return MyRoutineList.processData(myVeggieList);
    }

    @Transactional
    public void checkMyVeggieRoutine(Long routineId) {
        Optional<Routine> routine = selectRoutineByRoutineId(routineId);
        Date addedDate = dateManager.addDate(routine.get().getDate(), routine.get().getPeriod());
        myVeggieRepository.updateRoutinePeriod(routineId, addedDate);
    }








    public Optional<Routine> selectRoutineByRoutineId(Long routineId){
        return Optional.ofNullable(myVeggieRepository.findRoutineById(routineId)
                .orElseThrow(() -> new RoutineNotFountException("해당 루틴이 존재하지 않습니다.")));
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
