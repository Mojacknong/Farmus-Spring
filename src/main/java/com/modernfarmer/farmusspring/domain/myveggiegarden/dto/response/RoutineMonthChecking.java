package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class RoutineMonthChecking {

    private List<Date> date;

    public static RoutineMonthChecking of(List<Date> date){
        return new RoutineMonthChecking(date);
    }




}