package com.modernfarmer.farmusspring.domain.myveggiegarden.dto;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SortedMyLikeDiary {

    private Diary diary;
    private boolean myLike;

    public SortedMyLikeDiary(Diary diary, boolean myLike) {
        this.diary = diary;
        this.myLike = myLike;
    }

}
