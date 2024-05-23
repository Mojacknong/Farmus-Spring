package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyVeggieHelper {

    private final MyVeggieRepository myVeggieRepository;

    public MyVeggie getMyVeggieEntity(Long id) {
        return myVeggieRepository.findById(id).orElseThrow(() ->
                new MyVeggieGardenBaseException("존재하지 않는 나의 채소입니다.", MyVeggieGardenErrorCode.NOT_FOUND_VEGGIE));
    }
}
