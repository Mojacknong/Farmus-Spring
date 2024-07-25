package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class MyVeggieRoutine {

    private Boolean complete;
    private String content;
    private int period;
    private Long routineId;


    public static MyVeggieRoutine of(Routine routine){
        return new MyVeggieRoutine(
                routine.isComplete(),
                routine.getContent(),
                routine.getPeriod(),
                routine.getId());
    }

    public static List<MyVeggieRoutine> processData(List<Routine> routineList){
        return routineList.stream()
                .map(routine -> {
                    return MyVeggieRoutine.of(routine);
                })
                .toList();
    }
}
