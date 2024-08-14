package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.util.DateManager;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class DiaryCommentContent {

    private Boolean myComment;
    private String content;
    private String date;
    private String nickname;
    private String profileImage;
    private Long commentId;

    public static DiaryCommentContent of(Boolean check, DiaryComment diaryComment, User user){
        return new DiaryCommentContent(
                check,
                diaryComment.getComment(),
                DateManager.parsingDotDateTime(diaryComment.getCreatedDate()),
                user.getNickname(),
                user.getProfileImage(),
                diaryComment.getId()
        );
    }


    public static List<DiaryCommentContent> processData(List<DiaryComment> diaryCommentList, Long userId){
        return diaryCommentList.stream()
                .map(diaryComment -> {
                    User user = diaryComment.getUser();
                    boolean sortMyCommentResult = sortMyComment(user, userId);
                    return DiaryCommentContent.of(sortMyCommentResult, diaryComment, user );
                })
                .toList();
    }

    public static boolean sortMyComment(User user, Long userId){
        log.info(String.valueOf(user.getId()));
        log.info(String.valueOf(userId));
        if(Objects.equals(user.getId(), userId))
            return true;
        return false;
    }



}
