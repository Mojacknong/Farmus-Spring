package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import com.modernfarmer.farmusspring.domain.history.helper.HistoryHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.DeleteMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.MyVeggieUpdate;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggieRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SuccessFarmingRequestDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.helper.MyVeggieHelper;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import com.modernfarmer.farmusspring.domain.veggieinfo.helper.VeggieInfoHelper;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.VeggieInfoVo;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieGardenService {

    private final MyVeggieRepository myVeggieRepository;
    private final MyDetailMyVeggieDto myDetailMyVeggieDto;
    private final VeggieInfoHelper veggieInfoHelper;
    private final MyVeggieHelper myVeggieHelper;
    private final HistoryHelper historyHelper;
    private final S3Service s3Service;



    @Transactional
    public BaseResponseDto<?> settingMyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggieRequest) {
        String veggieInfoId = settingMyVeggieRequest.getVeggieInfoId();
        VeggieInfoVo veggieInfo = veggieInfoHelper.getVeggieInfo(veggieInfoId);
        addMyyVeggie(userId, settingMyVeggieRequest, veggieInfo);
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
        VeggieInfo veggieInfo = veggieInfoHelper.getVeggieInfoEntity(myVeggie.getVeggieInfoId());
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                SelectMyVeggieProfileResponse.of(
                        myVeggie.getNickname(),
                        myVeggie.getVeggieName(),
                        myVeggie.getVeggieImage(),
                        DateManager.parsingDotDate(myVeggie.getBirth()),
                        DateManager.calculateDay(myVeggie.getBirth(), new Date()),
                        checkFarmClubAffiliation(myVeggie),
                        veggieInfo.getSteps().size()
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

    @Transactional
    public void successFarming(SuccessFarmingRequestDto requestDto, MultipartFile image, Long userId) {
        String imageUrl = s3Service.uploadImage(image, "farm-result");
        HistoryVeggieDetail.HistoryPost farmResult = HistoryVeggieDetail.createHistoryPost(
                imageUrl,
                requestDto.content(),
                DateManager.parsingDotDateTime(LocalDateTime.now())
        );
        historyHelper.createVeggieHistoryDetail(userId, requestDto.myVeggieId(), farmResult);
        myVeggieHelper.deleteMyVeggie(requestDto.myVeggieId());
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

    private void addMyyVeggie(Long userId, SettingMyVeggieRequest settingMyVeggieRequest, VeggieInfoVo veggieInfoVo){

        MyVeggie newMyVeggie = MyVeggie.createMyVegetable(
                settingMyVeggieRequest.getNickname(),
                settingMyVeggieRequest.getBirth(),
                settingMyVeggieRequest.getVeggieInfoId(),
                veggieInfoVo.name(),
                veggieInfoVo.veggieImage(),
                User.builder().id(userId).build()
        );
        myVeggieRepository.save(newMyVeggie);
    }

}
