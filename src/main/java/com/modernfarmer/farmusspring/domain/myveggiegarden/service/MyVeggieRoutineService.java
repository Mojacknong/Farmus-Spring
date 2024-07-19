package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.RoutineMonthChecking;
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
import com.modernfarmer.farmusspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public RoutineMonthChecking selectRoutineCheckingAccordingToMonth(User user, Date month) {
        List<RoutineTime> routineTimeList = routineTimeRepository.findRoutineTimeAndRoutineAndMyVeggieByMonthWithUser(month, user);
        return RoutineMonthChecking.of(extractDate(routineTimeList));
    }

    @Transactional
    public List<MyVeggieRoutine> selectMyVeggieRoutineById(MyVeggie myVeggie) {
        List<Routine> routineList = myVeggieRepository.findMyVeggieRoutineById(myVeggie);
        return  MyVeggieRoutine.processData(routineList);
    }

    @Transactional
    public List<MyRoutineList>  selectRoutineAccordingToDate(User user, Date day) {
        List<MyVeggie> myVeggieList = myVeggieRepository.findMyVeggieAndRoutineTimeAndRoutineByUserWithDate(user, day);
        return mappingMyVeggieListData(myVeggieList, day);
    }


    public List<MyRoutineList> mappingMyVeggieListData(List<MyVeggie> myVeggieList, Date day){
        return myVeggieList.stream()
                .map(myVeggie -> MyRoutineList.of(myVeggie,mappingRoutineListData(myVeggie, day)))
                .toList();
    }

    private List<MyVeggieRoutine> mappingRoutineListData(MyVeggie myVeggie, Date day) {
        return myVeggie.getRoutines().stream()
                .filter(routine -> checkingRoutineTime(day, routine) != null)
                .map(routine -> MyVeggieRoutine.of(routine, checkingRoutineTime(day, routine)))
                .toList();
    }

    public Boolean checkingRoutineTime(Date date, Routine routine) {
        return routine.getRoutineTimes().stream()
                .filter(routineTime -> dateManager.formatDayDateToString(date).equals(dateManager.formatDayDateToString(routineTime.getDate())))
                .findFirst()
                .map(RoutineTime::getComplete)
                .orElse(null);
    }



    public List<Date> extractDate(List<RoutineTime> routineTimeList){
        return routineTimeList.stream()
                .map(RoutineTime::getDate)
                .toList();
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
