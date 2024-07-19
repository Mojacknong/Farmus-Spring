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

    private Boolean check;
    private String content;
    private int period;
    private Long routineId;


    public static MyVeggieRoutine of(Routine routine, Boolean check){
        return new MyVeggieRoutine(
                check,
                routine.getContent(),
                routine.getPeriod(),
                routine.getId()
        );
    }



    public static List<MyVeggieRoutine> processData(List<Routine> routineList){
        return routineList.stream()
                .map(routine -> {
                    boolean check;
                //    check = signRoutineCheck(routine.getDate());
                    check= true;
                    return MyVeggieRoutine.of(routine, check);
                }).toList();

    }


//    private static Boolean signRoutineCheck(Date date){
//        boolean check = true;
//        String date1 = String.valueOf(date);
//        LocalDate currentDate = LocalDate.now();
//        LocalTime fixedTime = LocalTime.of(0, 0, 0, 0);
//        LocalDateTime dateTime = LocalDateTime.of(currentDate, fixedTime);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//        String formattedDateTime = dateTime.format(formatter);
//        if (date1.equals(formattedDateTime)) {
//           check = false;
//        }
//        return check;
//    }

}
