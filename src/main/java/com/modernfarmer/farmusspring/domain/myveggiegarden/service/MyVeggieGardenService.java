package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggiRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieGardenService {

    private final MyVeggieRepository myVeggieRepository;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggi(Long userId, SettingMyVeggiRequest settingMyVeggiRequest) {

        addMyyVeggi(userId, settingMyVeggiRequest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    public MyVeggie getMyVeggie(Long userId){
        return myVeggieRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("채소가 존재하지 않습니다."));
    }

    private void addMyyVeggi(Long userId, SettingMyVeggiRequest settingMyVeggiRequest){

        MyVeggie newMyVeggie = MyVeggie.createMyVegetable(
                settingMyVeggiRequest.getNickname(),
                settingMyVeggiRequest.getBirh(),
                settingMyVeggiRequest.getVeggiInfoId(),
                User.builder().id(userId).build()
        );
        myVeggieRepository.save(newMyVeggie);
    }

}
