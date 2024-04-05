package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieDiaryService {
    private final MyVeggieRepository myVeggieRepository;

    private final S3Service s3Service;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggiDiary(
            MultipartFile multipartFile,
            String content,
            boolean isOpen,
            String state,
            Long myVeggieId
    ) throws IOException {

        String imageUrl = getImageUrl(multipartFile);
        log.info(String.valueOf(MyVeggie.builder().id(myVeggieId).build().getId()));

        MyVeggie myVeggie = myVeggieRepository.findMyVeggieById(myVeggieId);

  //      log.info(myVeggie.getNickname());
        addMyyVeggiDiary(
                content,
                isOpen,
                imageUrl,
                state,
             //   MyVeggie.builder().id(myVeggieId).build()
                myVeggie
        );
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }
    @Transactional
    public void addMyyVeggiDiary(
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
        log.info("start");
        myVeggie.addDiary(newDiary);
        log.info("finish");
    }

    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "dairyimage");
    }
}
