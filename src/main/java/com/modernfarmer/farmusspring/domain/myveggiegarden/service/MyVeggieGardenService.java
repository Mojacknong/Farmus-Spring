package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DeleteMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.SelectMyVeggieListDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.SelectMyVeggieListResponse;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieGardenService {

    private final MyVeggieRepository myVeggieRepository;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggieRequest) {

        addMyyVeggie(userId, settingMyVeggieRequest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @Transactional
    public BaseResponseDto<Void> selectMyVeggieList(Long userId) {
        List<MyVeggie> myVeggieList = bringMyVeggieData(userId);
        List<SelectMyVeggieListDto> selectMyVeggieLists = processingSimpleMyVeggieData(myVeggieList);
        return BaseResponseDto.of(SuccessCode.SUCCESS,SelectMyVeggieListResponse.of(selectMyVeggieLists));
    }

    @Transactional
    public BaseResponseDto<Void> deleteMyVeggie(DeleteMyVeggieRequest deleteMyVeggieRequest) {
        deleteMyVeggieById((deleteMyVeggieRequest.getMyVeggieId()));
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @Transactional
    public BaseResponseDto<Void> selectMyVeggieProfile(Long myVeggieId) {
        deleteMyVeggieById((deleteMyVeggieRequest.getMyVeggieId()));
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }





    public void deleteMyVeggieById(Long myVeggieId) {
        myVeggieRepository.deleteById(myVeggieId);
    }

    private List<MyVeggie> bringMyVeggieData(Long userId){
        return myVeggieRepository.findMyVeggieUserId(userId);
    }

    private List<SelectMyVeggieListDto> processingSimpleMyVeggieData(List<MyVeggie> myVeggieList){
        return myVeggieList.stream()
                .map(myVeggie -> new SelectMyVeggieListDto(myVeggie.getVeggieInfoId(),myVeggie.getNickname()))
                .collect(Collectors.toList());
    }



    public MyVeggie getMyVeggie(Long userId){
        return myVeggieRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("채소가 존재하지 않습니다."));
    }

    private void addMyyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggiRequest){

        MyVeggie newMyVeggie = MyVeggie.createMyVegetable(
                settingMyVeggiRequest.getNickname(),
                settingMyVeggiRequest.getBirh(),
                settingMyVeggiRequest.getVeggiInfoId(),
                settingMyVeggiRequest.getVeggieName(),
                settingMyVeggiRequest.getVeggieImage(),
                User.builder().id(userId).build()
        );
        myVeggieRepository.save(newMyVeggie);
    }

}
