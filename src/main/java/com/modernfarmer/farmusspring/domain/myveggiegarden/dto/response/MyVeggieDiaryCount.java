package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyVeggieDiaryCount {

    private int diaryCount;
    private int publicDiaryCount;

    public static MyVeggieDiaryCount of(int diaryCount, int publicDiaryCount){
        return new MyVeggieDiaryCount(
                diaryCount,
                publicDiaryCount
        );
    }

    public static MyVeggieDiaryCount processData(List<Diary> diaryList){

        return MyVeggieDiaryCount.of(diaryList.size(),selectPublicDiaryCount(diaryList));

    }

    public static int selectPublicDiaryCount(List<Diary> diaryList){
        return (int) diaryList.stream()
                .filter(diary -> diary.getIsOpen())
                .count();
        }
}
