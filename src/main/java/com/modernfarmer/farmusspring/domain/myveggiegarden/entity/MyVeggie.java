package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;

import com.modernfarmer.farmusspring.domain.farmclub.entity.UserFarmClub;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "my_veggie")
public class MyVeggie extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_veggie_id")
    private Long id;

    @Column(name = "veggie_nickname")
    private String nickname;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "veggie_info_id")
    private String veggieInfoId;

    @Column(name = "veggie_name")
    private String veggieName;

    @Column(name = "veggie_image")
    private String veggieImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "myVeggie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Routine> routines = new ArrayList<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "myVeggie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Diary> diaries = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_farm_club_id")
    private UserFarmClub userFarmClub;

    public static MyVeggie createMyVegetable(String nickname, Date birth, String veggieInfoId,
                                             String veggieName,
                                             String veggieImage,
                                             User user){
        MyVeggie newMyVeggie = MyVeggie.builder()
                .nickname(nickname)
                .birth(birth)
                .veggieInfoId(veggieInfoId)
                .veggieName(veggieName)
                .veggieImage(veggieImage)
                .user(user)
                .build();

        user.addMyVegetable(newMyVeggie);

        return newMyVeggie;

    }

    public void addRoutine(Routine routine) {
        routines.add(routine);
    }

    public void addDiary(Diary diary) {
        diaries.add(diary);
    }

    public void setUserFarmClub(UserFarmClub userFarmClub) {
        this.userFarmClub = userFarmClub;
    }
}
