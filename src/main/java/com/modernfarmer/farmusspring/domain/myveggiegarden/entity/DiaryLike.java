package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;




@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "diary_like")
public class DiaryLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;


    public static DiaryLike createDiaryLike(Diary diary, User user){
        DiaryLike newDiaryLike = DiaryLike.builder()
                .user(user)
                .diary(diary)
                .build();

        diary.addDiaryLike(newDiaryLike);
        user.addDiaryLike(newDiaryLike);

        return newDiaryLike;

    }
}
