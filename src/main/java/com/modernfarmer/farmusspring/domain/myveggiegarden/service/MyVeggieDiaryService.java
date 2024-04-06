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

@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieDiaryService {

    private final S3Service s3Service;
    private final MyVeggieGardenService myVeggieGardenService;

    @Transactional
    public BaseResponseDto<Void> settingMyVeggieDiary(
            MultipartFile multipartFile,
            String content,
            boolean isOpen,
            String state,
            Long myVeggieId
    ) throws IOException {

        String imageUrl = getImageUrl(multipartFile);
        addMyyVeggieDiary(
                content,
                isOpen,
                imageUrl,
                state,
                myVeggieId
        );
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    private void addMyyVeggieDiary(
            String content,
            boolean isOpen,
            String image,
            String state,
            Long myVeggieId
    ){
        MyVeggie myVeggie = myVeggieGardenService.getMyVeggie(myVeggieId);


        Diary newDiary = Diary.createDiary(
                content,
                isOpen,
                image,
                state,
                myVeggie
        );
        myVeggie.addDiary(newDiary);
    }

    private String getImageUrl(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadImage(multipartFile, "dairyimage");
    }
}
