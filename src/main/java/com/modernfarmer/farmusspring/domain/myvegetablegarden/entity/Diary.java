package com.modernfarmer.farmusspring.domain.myvegetablegarden.entity;


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
@Entity(name = "diary")
public class Diary extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "image")
    private String image;

    @Column(name = "state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_vegetable_id")
    private MyVegetable myVegetable;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<DiaryComment> diaryComments = new ArrayList<>();

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<DiaryLike> diaryLikes = new ArrayList<>();



    public static Diary createDiary(String content, Boolean isOpen, String image, String state, MyVegetable myVegetable){
        Diary newDiary = Diary.builder()
                .content(content)
                .isOpen(isOpen)
                .image(image)
                .state(state)
                .myVegetable(myVegetable)
                .build();

        myVegetable.addDiary(newDiary);

        return newDiary;

    }

    public void addDiaryComment(DiaryComment diaryComment) {
        diaryComments.add(diaryComment);
    }

    public void addDiaryLike(DiaryLike diaryLike) {
        diaryLikes.add(diaryLike);
    }
}
