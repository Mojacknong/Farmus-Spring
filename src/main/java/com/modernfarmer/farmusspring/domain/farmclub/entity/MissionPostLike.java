package com.modernfarmer.farmusspring.domain.farmclub.entity;

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
@Entity(name = "mission_post_like")
public class MissionPostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_post_id")
    private MissionPost missionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static MissionPostLike createMissionPostLike(MissionPost missionPost, User user){
        MissionPostLike newMissionPostLike = MissionPostLike.builder()
                .missionPost(missionPost)
                .user(user)
                .build();

        missionPost.addLike(newMissionPostLike);
        user.addMissionPostLike(newMissionPostLike);

        return newMissionPostLike;
    }
}
