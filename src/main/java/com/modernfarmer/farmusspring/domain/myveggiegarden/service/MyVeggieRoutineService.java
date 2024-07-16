package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.RoutineTime;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.RoutineNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.RoutineTimeNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.RoutineRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.RoutineTimeRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieRoutineService {

    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;
    private final RoutineTimeRepository routineTimeRepository;
    private final RoutineRepository routineRepository;
    private final DateManager dateManager;


    @Transactional
    public void modifyRoutine(RoutineUpdate routineUpdate) {
        verifyRoutine(routineRepository.findRoutineById(routineUpdate.getRoutineId()));
        routineRepository.updateRoutine(routineUpdate.getRoutineId(), routineUpdate.getContent(), routineUpdate.getPeriod());
    }

    @Transactional
    public void eraseRoutine(RoutineDelete routineDelete) {
        verifyRoutine(routineRepository.findRoutineById(routineDelete.getRoutineId()));
        routineRepository.deleteRoutine(routineDelete.getRoutineId());
    }

    @Transactional
    public void settingMyVeggieRoutine(SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest) {
        Routine routine = addMyVeggieRoutine(settingMyVeggieRoutineReqeuest);
        addRoutineTime(new Date(), false, routine);
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
    public void checkMyVeggieRoutine(Long routineId, Long routineTimeId) {
       // Optional<Routine> routine = selectRoutineById(routineId);
        Optional<Routine> routine = routineRepository.findRoutineById(routineId);
        verifyRoutine(routine);
     //   Optional<RoutineTime> routineTime = selectRoutineTimeByIdAndRoutineId(routine.get(), routineTimeId);
        Optional<RoutineTime> routineTime = routineTimeRepository.findRoutineTimeByIdAndRoutineId(routine.get(), routineTimeId);
        verifyRoutineTime(routineTime);
        routineTimeRepository.updateRoutineTimeComplete(routine.get(), routineTimeId);
        Date addedDate = dateManager.addDate(routineTime.get().getDate(), routine.get().getPeriod());
        addRoutineTime(addedDate, false, routine.get());
    }





    private void addRoutineTime(Date date, boolean complete, Routine routine){
        RoutineTime newRoutineTime = RoutineTime.createRoutineTime(date, complete, routine);
        routine.addRoutineTime(newRoutineTime);
    }


    public void verifyRoutineTime(Optional<RoutineTime> routineTime){
        if(routineTime.isEmpty())
            throw new RoutineTimeNotFoundException("존재하지 않는 루틴타임입니다.", MyVeggieGardenErrorCode.NOT_FOUND_ROUTINE_TIME);
    }

    public void verifyRoutine(Optional<Routine> routine){
        if(routine.isEmpty())
            throw new RoutineNotFoundException("존재하지 않는 루틴입니다.", MyVeggieGardenErrorCode.NOT_FOUND_ROUTINE);

    }



    private Routine addMyVeggieRoutine(SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(settingMyVeggieRoutineReqeuest.getMyVeggieId());
        Routine newRoutine = Routine.createRoutine(
                settingMyVeggieRoutineReqeuest.getContent(),
                settingMyVeggieRoutineReqeuest.getPeriod(),
                myVeggie
        );
        myVeggie.addRoutine(newRoutine);
        return newRoutine;
    }


}
