package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.MyVeggieGardenBaseException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
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

    public MyVeggieVo getMyVeggieInfo(Long userId, String veggieInfoId) {
        return myVeggieRepository.findMyVeggieInfo(userId, veggieInfoId).orElseThrow(() ->
                new MyVeggieGardenBaseException("채소 데이터를 찾을 수 없습니다.", MyVeggieGardenErrorCode.NO_VEGGIE_FOR_REGISTER)
        );
    }
}
