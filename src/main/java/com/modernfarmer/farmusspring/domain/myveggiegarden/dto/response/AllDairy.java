package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary;
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
    private String state;
    private Long diaryId;
    private int likeCount;
    private int commentCount;
    private boolean myLike;

    public static AllDairy of(SortedMyLikeDiary diaryAll, String date){
        return new AllDairy(
                date,
                diaryAll.getDiary().getImage(),
                diaryAll.getDiary().getContent(),
                diaryAll.getDiary().getIsOpen(),
                diaryAll.getDiary().getState(),
                diaryAll.getDiary().getId(),
                diaryAll.getDiary().getDiaryLikes().size(),
                diaryAll.getDiary().getDiaryComments().size(),
                diaryAll.isMyLike()
        );
    }
    public static List<AllDairy> processData(List<SortedMyLikeDiary> diaryAllList){
        return diaryAllList.stream()
                .map(diaryAll -> AllDairy.of(diaryAll, formatDate(diaryAll.getDiary().getCreatedDate())))
                .toList();
    }
}
