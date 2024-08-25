package com.modernfarmer.farmusspring.domain.farmclub.entity;

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
@Entity(name = "mission_post")
public class MissionPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int stepNum;

    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionPostLike> missionPostLikes = new ArrayList<>();


    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionPostComment> missionPostComments = new ArrayList<>();


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_farm_club_id")
    private UserFarmClub userFarmClub;

    public static MissionPost createMissionPost(String content, int stepNum, String image, UserFarmClub userFarmClub){
        MissionPost newMissionPost = MissionPost.builder()
                .content(content)
                .stepNum(stepNum)
                .image(image)
                .userFarmClub(userFarmClub)
                .build();

        userFarmClub.addMissionPost(newMissionPost);

        return newMissionPost;
    }

    public void addLike(MissionPostLike missionPostLike) {
        missionPostLikes.add(missionPostLike);
    }

    public void addComment(MissionPostComment missionPostComment) {
        missionPostComments.add(missionPostComment);
    }
}
