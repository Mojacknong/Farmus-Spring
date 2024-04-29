package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public List<AllDairy> processData(List<Diary> diaryList){
        return diaryList.stream()
                .map(diary -> AllDairy.of(diary, formatDate(diary.getCreatedDate())))
                .toList();
    }
    public String formatDate(LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

}
