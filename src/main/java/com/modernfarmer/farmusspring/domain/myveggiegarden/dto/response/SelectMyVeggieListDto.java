package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


//@AllArgsConstructor
//@Getter
//public class SelectMyVeggieListDto {
//
//    public static List<SelectMyVeggieListResponse> processData(List<MyVeggie> myVeggieList){
//        return myVeggieList.stream()
//                .map(myVeggie -> SelectMyVeggieListResponse.of(myVeggie.getId(),myVeggie.getNickname()))
//                .toList();
//    }
//}
