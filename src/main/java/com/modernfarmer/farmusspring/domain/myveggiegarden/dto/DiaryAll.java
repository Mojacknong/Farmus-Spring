package com.modernfarmer.farmusspring.domain.myveggiegarden.dto;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public class DiaryAll {

    private Diary diary;
    private boolean myLike;

    public DiaryAll(Diary diary, boolean myLike) {
        this.diary = diary;
        this.myLike = myLike;
    }

}
