package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

import static com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager.formatDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AllDairy {

    private String date;
    private String image;
    private String content;
    private Boolean isOpen;

    public static AllDairy of(Diary diary, String date){
        return new AllDairy(
                date,
                diary.getImage(),
                diary.getContent(),
                diary.getIsOpen()
        );
    }

    public static List<AllDairy> processData(List<Diary> diaryList){
        return diaryList.stream()
                .map(diary -> AllDairy.of(diary, formatDate(diary.getCreatedDate())))
                .toList();
    }


}
