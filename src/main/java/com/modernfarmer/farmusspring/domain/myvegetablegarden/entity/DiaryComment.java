package com.modernfarmer.farmusspring.domain.myvegetablegarden.entity;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;




@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "diary_comment")
public class DiaryComment extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_comment_id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public static DiaryComment createDiaryComment(String content, Diary diary, User user){
        DiaryComment newDiaryComment = DiaryComment.builder()
                .comment(content)
                .user(user)
                .diary(diary)
                .build();

        diary.addDiaryComment(newDiaryComment);
        user.addDiaryComment(newDiaryComment);

        return newDiaryComment;

    }


}
