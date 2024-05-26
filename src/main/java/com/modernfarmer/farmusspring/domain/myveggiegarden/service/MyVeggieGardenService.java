package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DeleteMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.MyVeggieUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;


import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieGardenService {

    private final MyVeggieRepository myVeggieRepository;
    private final DateManager dateManager;
    private final MyDetailMyVeggieDto myDetailMyVeggieDto;



    @Transactional
    public BaseResponseDto<Void> settingMyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggieRequest) {

        addMyyVeggie(userId, settingMyVeggieRequest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @Transactional
    public BaseResponseDto<List<SelectMyVeggieListResponse>> selectMyVeggieList(Long userId) {
        List<MyVeggie> myVeggieList = bringMyVeggieData(userId);
        List<SelectMyVeggieListResponse> selectMyVeggieLists = SelectMyVeggieListDto.processData(myVeggieList);
        return BaseResponseDto.of(SuccessCode.SUCCESS,selectMyVeggieLists);
    }

    @Transactional
    public BaseResponseDto<Void> deleteMyVeggie(DeleteMyVeggieRequest deleteMyVeggieRequest) {
        deleteMyVeggieById((deleteMyVeggieRequest.getMyVeggieId()));
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @Transactional
    public BaseResponseDto<SelectMyVeggieProfileResponse> selectMyVeggieProfile(Long myVeggieId) {
        MyVeggie myVeggie = selectMyVeggieAndFarmClub(myVeggieId);
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                SelectMyVeggieProfileResponse.of(
                        myVeggie.getVeggieName(),
                        myVeggie.getVeggieImage(),
                        dateManager.parsingDotDate(myVeggie.getBirth()),
                        dateManager.calculateDay(myVeggie.getBirth(), new Date()),
                        checkFarmClubAffiliation(myVeggie)
                        ));
    }


    @Transactional
    public BaseResponseDto<List<MyDetailMyVeggie>> selectDetailMyVeggieList(Long userId) {

        List<MyVeggie> myVeggieList = bringMyVeggieData(userId);
        List<MyDetailMyVeggie> selectMyVeggieList = myDetailMyVeggieDto.processData(myVeggieList);
        return BaseResponseDto.of(SuccessCode.SUCCESS,selectMyVeggieList);
    }

    @Transactional
    public void myVeggieUpdate(MyVeggieUpdate myVeggieUpdate) {
        myVeggieRepository.updateMyVeggie(myVeggieUpdate.getMyVeggieId(), myVeggieUpdate.getNickname(), myVeggieUpdate.getBirth());
    }



    public int checkFarmClubAffiliation(MyVeggie myVeggie){
        if(myVeggie.getUserFarmClub() == null)
            return -1;
        return myVeggie.getUserFarmClub().getCurrentStep();
    }


    public MyVeggie selectMyVeggieAndFarmClub(Long myVeggieId){
        return myVeggieRepository.findMyVeggieAndFarmClub(myVeggieId);

    }


    public void deleteMyVeggieById(Long myVeggieId) {
        myVeggieRepository.deleteById(myVeggieId);
    }

    private List<MyVeggie> bringMyVeggieData(Long userId){
        return myVeggieRepository.findMyVeggieUserId(userId);
    }





    public MyVeggie getMyVeggie(Long userId){
        return myVeggieRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("채소가 존재하지 않습니다."));
    }

    private void addMyyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggieRequest){

        MyVeggie newMyVeggie = MyVeggie.createMyVegetable(
                settingMyVeggieRequest.getNickname(),
                settingMyVeggieRequest.getBirth(),
                settingMyVeggieRequest.getVeggieInfoId(),
                settingMyVeggieRequest.getVeggieName(),
                settingMyVeggieRequest.getVeggieImage(),
                User.builder().id(userId).build()
        );
        myVeggieRepository.save(newMyVeggie);
    }

}
