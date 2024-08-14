package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineSetting;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.RoutineMonthChecking;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.RoutineNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.RoutineRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieRoutineService {

    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;
    private final DateManager dateManager;
    private final RoutineRepository routineRepository;


    @Transactional
    public void settingVeggieRoutine(Long veggieId, String content, int period)  {
        addVeggieRoutine(veggieId, content, period, new Date());
    }

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
    public List<MyVeggieRoutine> selectMyVeggieRoutineById(MyVeggie myVeggie) {
        List<Routine> routineList = routineRepository.findRoutineByIdAndToday(myVeggie);
        return  MyVeggieRoutine.processData(routineList);
    }

    @Transactional
    public List<MyRoutineList> selectMyVeggieRoutine(Long userId) {
        log.info("채소별 리스트 서비스 시작");
        List<MyVeggie> myVeggieList = myVeggieRepository.findMyVeggieAndRoutine(userId);
        return MyRoutineList.processData(myVeggieList);
    }

    @Transactional
    public RoutineMonthChecking selectRoutineCheckingAccordingToMonth(User user, Date month) {
        List<Routine> routineTimeList = routineRepository.findRoutineAndRoutineAndMyVeggieByMonthWithUser(month, user);
        return RoutineMonthChecking.of(extractDate(routineTimeList));
    }

    @Transactional
    public List<MyRoutineList>  selectRoutineAccordingToDate(User user, Date day) {
        List<MyVeggie> myVeggieList = myVeggieRepository.findMyVeggieAndRoutineByUserWithDate(user);
        return mappingMyVeggieListData(myVeggieList, day);
    }

    public List<MyRoutineList> mappingMyVeggieListData(List<MyVeggie> myVeggieList, Date day){
        return myVeggieList.stream()
                .map(myVeggie -> MyRoutineList.of(myVeggie, mappingRoutineListData(myVeggie, day)))
                .toList();
    }


    private List<MyVeggieRoutine> mappingRoutineListData(MyVeggie myVeggie, Date day) {
        return myVeggie.getRoutines().stream()
                .filter(routine -> Objects.equals(DateManager.formatDayDateToString(day), routine.getDate().toString()))
                .map(MyVeggieRoutine::of)
                .toList();
    }

    @Transactional
    public void checkVeggieRoutine(Long routineId) {
        Optional<Routine> routine = routineRepository.findRoutineById(routineId);
        verifyRoutine(routine);
        Date addedDate = DateManager.addDate(routine.get().getDate(), routine.get().getPeriod());
        routineRepository.updateRoutineComplete(routine.get(), routineId);
        addVeggieRoutine(routine.get().getMyVeggie().getId(), routine.get().getContent(), routine.get().getPeriod(), addedDate);
    }

    public void verifyRoutine(Optional<Routine> routine){
        if(routine.isEmpty())
            throw new RoutineNotFoundException("존재하지 않는 루틴입니다.", MyVeggieGardenErrorCode.NOT_FOUND_ROUTINE);
    }

    private void addVeggieRoutine(Long veggieId, String content, int period, Date date){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(veggieId);
        Routine newRoutine = Routine.createRoutine(date,content,period, myVeggie, false);
        myVeggie.addRoutine(newRoutine);
    }

    public List<Date> extractDate(List<Routine> routineList){
        return routineList.stream()
                .map(Routine::getDate)
                .distinct()
                .toList();
    }


}
