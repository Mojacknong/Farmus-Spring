package com.modernfarmer.farmusspring.domain.myveggiegarden.util;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.RoutineRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieRoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoutineScheduler {

    private final RoutineRepository routineRepository;
    private final MyVeggieRoutineService myVeggieRoutineService;

    @Scheduled(cron = "2 0 0 * * *")
    public void run() {
        List<Routine> routineList = routineRepository.findRoutine();
        addRoutine(routineList);
        log.info("루틴 스케줄러 실행 완료");
    }

    private void addRoutine(List<Routine> routineList){
        Map<Boolean, List<Routine>> sortedRoutines = routineList.stream()
                .collect(Collectors.groupingBy(Routine::isComplete));
        complete(sortedRoutines.get(true));
        notComplete(sortedRoutines.get(false));
    }

    private void complete(List<Routine> routineList){
        if (routineList != null && !routineList.isEmpty()) {
            routineList
                    .forEach(routine -> myVeggieRoutineService.checkVeggieRoutine(routine.getId()));
        }
    }

    private void notComplete(List<Routine> routineList){
        if (routineList != null && !routineList.isEmpty()) {
            routineList
                    .forEach(routine -> myVeggieRoutineService.addRoutineOneDay(routine.getId()));
        }
    }
}
