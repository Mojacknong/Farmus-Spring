package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@Getter
@Component
public class MyDetailMyVeggieDto {


    private final DateManager dateManager;

    public List<MyDetailMyVeggie> processData(List<MyVeggie> myVeggieList){

        return myVeggieList.stream()
                .map(myVeggie -> MyDetailMyVeggie.of(
                        myVeggie.getNickname(),
                        myVeggie.getVeggieImage(),
                        myVeggie.getVeggieName(),
                        dateManager.dateParsing(myVeggie.getBirth()),
                        dateManager.dayBetween(myVeggie.getBirth(), new Date()),
                        myVeggie.getId()

                )).toList();
    }

}
