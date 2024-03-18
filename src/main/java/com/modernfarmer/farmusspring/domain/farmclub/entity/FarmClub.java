package com.modernfarmer.farmusspring.domain.farmclub.entity;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "farm_club")
public class FarmClub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_club_id")
    private Long id;

    @Column(nullable = false)
    private String veggieInfoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private int maxUser;

    @Column(nullable = false)
    private LocalDate startedAt;

    @OneToMany(mappedBy = "farmClub")
    @Builder.Default
    private List<UserFarmClub> userFarmClubs = new ArrayList<>();

    @OneToMany(mappedBy = "farmClub")
    @Builder.Default
    private List<Diary> diaries = new ArrayList<>();

    public static FarmClub createFarmClub(String veggieInfoId, String name, String description, String difficulty, int maxUser, LocalDate startedAt){
        return FarmClub.builder()
                .veggieInfoId(veggieInfoId)
                .name(name)
                .description(description)
                .difficulty(difficulty)
                .maxUser(maxUser)
                .startedAt(startedAt)
                .build();
    }

    public void addUserFarmClub(UserFarmClub userFarmClub){
        this.userFarmClubs.add(userFarmClub);
    }

    public void addDiary(Diary diary){
        this.diaries.add(diary);
    }
}
