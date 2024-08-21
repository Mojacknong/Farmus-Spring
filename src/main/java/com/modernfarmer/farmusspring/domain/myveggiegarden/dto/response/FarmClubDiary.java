package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FarmClubDiary {

    private Long diaryId;
    private String diaryImage;
    private String diaryContent;
    private String writeDateTime;
    private int likeCount;
    private int commentCount;
    private String nickname;
    private String profileImage;
    private boolean myLike;
    private boolean myDiary;
    private String state;

    public static FarmClubDiary of(Diary diary, User user, String writeDateTime, int likeCount, int commentCount, boolean myLike, boolean myDiary, String status){
        return new FarmClubDiary(
                diary.getId(),
                diary.getImage(),
                diary.getContent(),
                writeDateTime,
                likeCount,
                commentCount,
                user.getNickname(),
                user.getProfileImage(),
                myLike,
                myDiary,
                status
        );
    }

}
