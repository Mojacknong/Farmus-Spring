package com.modernfarmer.farmusspring.domain.myveggiegarden.service;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.AllDairy;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.CheckTodayDiaryResponse;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.MyVeggieDiaryCount;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.SelectDiaryOneResponse;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenSuccessCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.MyVeggieRepository;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import com.modernfarmer.farmusspring.infra.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class MyVeggieDiaryService {

    private final S3Service s3Service;
    private final MyVeggieGardenService myVeggieGardenService;
    private final MyVeggieRepository myVeggieRepository;

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


    @Transactional
    public BaseResponseDto<CheckTodayDiaryResponse> checkTodayDiary(
            MyVeggie myVeggie
    ) {

        Diary diary = selectTodayDiary(myVeggie);
        boolean state = verifyDiaryState(diary);
        return BaseResponseDto.of(SuccessCode.SUCCESS,CheckTodayDiaryResponse.of(state));
    }
    @Transactional
    public MyVeggieDiaryCount selectDiaryCount(MyVeggie myVeggie) {

        List<Diary> diaryList = myVeggieRepository.findDiariesByMyVeggie(myVeggie);
        return MyVeggieDiaryCount.processData(diaryList);
    }

    @Transactional
    public List<AllDairy> selectDiaryAll(MyVeggie myVeggie) {

        List<Diary> diaryList = selectDiaryByMyVeggie(myVeggie);
        return AllDairy.processData(diaryList);
    }



    @Transactional
    public BaseResponseDto<SelectDiaryOneResponse> selectDiaryOne(
            MyVeggie myVeggie
    )  {
        List<Diary> diaryList = selectDiaryByMyVeggie(myVeggie);
        if(diaryList.isEmpty()) {
            return BaseResponseDto.of(MyVeggieGardenSuccessCode.NOT_FOUND_DIARY, null);
        }
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                SelectDiaryOneResponse.of(
                        diaryList.get(0).getImage(),
                        diaryList.get(0).getContent(),
                        diaryList.get(0).getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ));
    }



    public boolean verifyDiaryState(Diary diary){

        if(diary == null){
            return true;
        }
        return  false;
    }


    public Diary selectTodayDiary(MyVeggie myVeggie){
        return myVeggieRepository.findDiariesByMyVeggieAndToday(myVeggie);
    }

    public List<Diary> selectDiaryByMyVeggie(MyVeggie myVeggie){
        return myVeggieRepository.findDiariesByMyVeggie(myVeggie);
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
