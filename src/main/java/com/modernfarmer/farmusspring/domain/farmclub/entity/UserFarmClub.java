package com.modernfarmer.farmusspring.domain.farmclub.entity;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "user_farm_club")
public class UserFarmClub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_farm_club_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int currentStep;

    @Column(nullable = false)
    private String currentStepName;

    @Column(nullable = false)
    private boolean isComplete;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "userFarmClub", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MissionPost> missionPosts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_club_id")
    private FarmClub farmClub;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_veggie_id")
    private MyVeggie myVeggie;

    public static UserFarmClub createUserFarmClub(Long userId, String currentStepName, FarmClub farmClub, MyVeggie myVeggie){
        UserFarmClub newUserFarmClub = UserFarmClub.builder()
                .userId(userId)
                .currentStep(1)
                .currentStepName(currentStepName)
                .farmClub(farmClub)
                .myVeggie(myVeggie)
                .isComplete(false)
                .build();

        farmClub.addUserFarmClub(newUserFarmClub);
        myVeggie.setUserFarmClub(newUserFarmClub);

        return newUserFarmClub;
    }

    public void updateComplete(){
        this.isComplete = true;
    }

    public void updateStep(String nextStep){
        this.currentStep = this.getCurrentStep() + 1;
        this.currentStepName = nextStep;
    }

    public void addMissionPost(MissionPost missionPost){
        this.missionPosts.add(missionPost);
    }
}
