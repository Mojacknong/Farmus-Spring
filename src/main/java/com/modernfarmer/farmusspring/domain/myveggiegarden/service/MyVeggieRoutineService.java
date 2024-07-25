package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.RoutineUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRoutineReqeuest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyRoutineList;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieRoutine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.RoutineNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.RoutineRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
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
    private final DateManager dateManager;
    private final RoutineRepository routineRepository;



    @Transactional
    public void settingMyVeggieRoutine(SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest)  {
        addMyyVeggieRoutine(settingMyVeggieRoutineReqeuest);
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
    public void checkVeggieRoutine(Long routineId) {
        Optional<Routine> routine = routineRepository.findRoutineById(routineId);
        verifyRoutine(routine);
        Date addedDate = dateManager.addDate(routine.get().getDate(), routine.get().getPeriod());
        myVeggieRepository.updateRoutinePeriod(routineId, addedDate);
    }





    public void verifyRoutine(Optional<Routine> routine){
        if(routine.isEmpty())
            throw new RoutineNotFoundException("존재하지 않는 루틴입니다.", MyVeggieGardenErrorCode.NOT_FOUND_ROUTINE);
    }



//
//    public Optional<Routine> selectRoutineByRoutineId(Long routineId){
//        return Optional.ofNullable(myVeggieRepository.findRoutineById(routineId)
//                .orElseThrow(() -> new RoutineNotFountException("해당 루틴이 존재하지 않습니다.")));
//    }

    private void addMyyVeggieRoutine(SettingMyVeggieRoutineReqeuest settingMyVeggieRoutineReqeuest){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(settingMyVeggieRoutineReqeuest.getMyVeggieId());
        Routine newRoutine = Routine.createRoutine(
                new Date(),
                settingMyVeggieRoutineReqeuest.getContent(),
                settingMyVeggieRoutineReqeuest.getPeriod(),
                myVeggie,
                false
        );
        myVeggie.addRoutine(newRoutine);
    }


}
