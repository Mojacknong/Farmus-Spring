package com.modernfarmer.farmusspring.domain.user.entity;

import com.modernfarmer.farmusspring.domain.myvegetablegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myvegetablegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.myvegetablegarden.entity.MyVegetable;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private int userNumber;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String level;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserFirebaseToken> userFirebaseTokens = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserMotivation> userMotivations = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MyVegetable> myVegetables = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<DiaryComment> diaryComments = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<DiaryLike> diaryLikes = new ArrayList<>();


    public static User createUser(String role, int userNumber, String profileImage){
        User newUser = User.builder()
                .role(role)
                .userNumber(userNumber)
                .profileImage(profileImage)
                .build();

        return newUser;

    }

    public void addUserFirebaseToken(UserFirebaseToken userFirebaseToken) {
        userFirebaseTokens.add(userFirebaseToken);
    }

    public void addUserMotivation(UserMotivation userMotivation) {
        userMotivations.add(userMotivation);
    }

    public void addMyVegetable(MyVegetable myVegetable) {
        myVegetables.add(myVegetable);
    }

    public void addDiaryComment(DiaryComment diaryComment) {
        diaryComments.add(diaryComment);
    }

    public void addDiaryLike(DiaryLike diaryLike) {
        diaryLikes.add(diaryLike);
    }


}