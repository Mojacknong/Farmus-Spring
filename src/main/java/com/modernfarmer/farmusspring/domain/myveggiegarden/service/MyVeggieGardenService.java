package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.SettingMyVeggiRequest;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieGardenService {

    private final MyVeggieRepository myVeggieRepository;
    private final S3Service s3Service;


    @Transactional
    public BaseResponseDto<Void> settingMyVeggi(Long userId, SettingMyVeggiRequest settingMyVeggiRequest) {

        addMyyVeggi(userId, settingMyVeggiRequest);
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @Transactional
    public BaseResponseDto<Void> settingMyVeggiDiary(
            MultipartFile multipartFile,
            String content,
            boolean isOpen,
            String state,
            Long myVeggieId
    ) throws IOException {

        String imageUrl = getImageUrl(multipartFile);
        addMyyVeggiDiary(
                content,
                isOpen,
                imageUrl,
                state,
                MyVeggie.builder().veggieInfoId(myVeggieId).build()
        );
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    private void addMyyVeggiDiary(
            String content,
            boolean isOpen,
            String image,
            String state,
            MyVeggie myVeggie
    ){
        Diary newDiary = Diary.createDiary(
                content,
                isOpen,
                image,
                state,
                myVeggie
        );
       myVeggie.addDiary(newDiary);
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

    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "dairyimage");
    }

}
