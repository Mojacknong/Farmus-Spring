package com.modernfarmer.farmusspring.domain.farmclub.entity;

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
@Entity(name = "mission_post")
public class MissionPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long step_num;

    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MissionPostLike> missionPostLikes = new ArrayList<>();

    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MissionPostComment> missionPostComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_farm_club_id")
    private UserFarmClub userFarmClub;

    public static MissionPost createMissionPost(String content, Long step_num, String image, UserFarmClub userFarmClub){
        MissionPost newMissionPost = MissionPost.builder()
                .content(content)
                .step_num(step_num)
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
