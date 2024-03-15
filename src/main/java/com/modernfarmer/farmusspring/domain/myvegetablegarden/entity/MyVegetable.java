package com.modernfarmer.farmusspring.domain.myvegetablegarden.entity;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "my_vegetable")
public class MyVegetable extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_vegetable_id")
    private Long id;

    @Column(name = "veggie_nickname")
    private String nickname;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "vegetable_id")
    private String vegetableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "myVegetable", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "myVegetable", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Diary> diaries = new ArrayList<>();


    public static MyVegetable createMyVegetable(String nickname, Date birth, String vegetableId, User user){
        MyVegetable newMyVegetable= MyVegetable.builder()
                .nickname(nickname)
                .birth(birth)
                .vegetableId(vegetableId)
                .user(user)
                .build();

        user.addMyVegetable(newMyVegetable);

        return newMyVegetable;

    }

    public void addRoutine(Routine routine) {
        routines.add(routine);
    }

    public void addDiary(Diary diary) {
        diaries.add(diary);
    }

}
