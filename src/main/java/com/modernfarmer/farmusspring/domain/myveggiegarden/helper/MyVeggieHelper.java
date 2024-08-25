package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyVeggieHelper {

    private final MyVeggieRepository myVeggieRepository;

    public MyVeggie getMyVeggieEntity(Long id) {
        return myVeggieRepository.findById(id).orElseThrow(() ->
                new MyVeggieGardenBaseException("존재하지 않는 나의 채소입니다.", MyVeggieGardenErrorCode.NOT_FOUND_VEGGIE));
    }

    public MyVeggieVo getMyVeggieInfo(Long userId, String veggieInfoId) {
        return myVeggieRepository.findMyVeggieInfo(userId, veggieInfoId).orElse(MyVeggieVo.of(0L, "", ""));
    }

    public List<MyVeggieVo> getMyVeggieInfo(Long userId) {
        return myVeggieRepository.findMyVeggieInfoForCreate(userId);
    }

    public boolean checkMyVeggie(Long userId) {
        return myVeggieRepository.existsByUserId(userId);
    }

    public void deleteMyVeggie(Long id) {
        myVeggieRepository.deleteById(id);
    }

    public void deleteMyVeggiesByUserId(Long userId){
        myVeggieRepository.deleteMyVeggiesByUserId(userId);
    }


    public List<Diary> getDiariesByMyVeggie(MyVeggie myVeggie) {
        return myVeggieRepository.findDiariesByMyVeggie(myVeggie);
    }
}
