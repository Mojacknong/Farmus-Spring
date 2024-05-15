package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyRoutineList {

    private String nickname;
    private String veggieName;
    private List<MyVeggieRoutine> myVeggieRoutineList;


    public static MyRoutineList of(MyVeggie myVeggie, List<MyVeggieRoutine> myVeggieRoutineList){
        return new MyRoutineList(
                myVeggie.getNickname(),
                myVeggie.getVeggieName(),
                myVeggieRoutineList
        );
    }


    public static List<MyRoutineList> processData(List<MyVeggie> myVeggieList){
        return myVeggieList.stream()
                .map(myVeggie -> {

                    return MyRoutineList.of(myVeggie,checkRoutine(myVeggie));
                })
                .toList();
    }

    private static List<MyVeggieRoutine> checkRoutine(MyVeggie myVeggie){

        if(myVeggie.getRoutines().isEmpty()){
            return new ArrayList<>();
        }
        return myVeggie.getRoutines().stream()
                .map(routine -> {

                    return new MyVeggieRoutine(true, routine.getContent(), routine.getPeriod(), routine.getId() );
                        })
                .toList();
    }

}
