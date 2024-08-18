package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class DiaryInteractionsDto {

    private List<DiaryCommentContent> diaryCommentContent;
    private int likeCount;
    private int commentCount;
    private boolean myLike;

    public static DiaryInteractionsDto of(List<DiaryCommentContent> diaryCommentContentList, int likeCount, int commentCount, boolean myLike){
        return new DiaryInteractionsDto(
                diaryCommentContentList,
                likeCount,
                commentCount,
                myLike
        );
    }
}
