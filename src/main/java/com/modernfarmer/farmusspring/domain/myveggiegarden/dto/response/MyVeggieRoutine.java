package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyVeggieRoutine {

    private Boolean check;
    private String content;
    private int period;


    public static MyVeggieRoutine of(Routine routine, Boolean check){
        return new MyVeggieRoutine(
                check,
                routine.getContent(),
                routine.getPeriod()
        );
    }

    public List<MyVeggieRoutine> processData(List<Routine> routineList){
        return routineList.stream()
                .map(routine -> {

                    check = signRoutineCheck(routine.getDate());
                    return MyVeggieRoutine.of(routine, check);
                })
                .toList();
    }


    private Boolean signRoutineCheck(Date date){
        check = true;
        if (date.equals(LocalDate.now())) {
           check = false;
        }
        return check;
    }

}
