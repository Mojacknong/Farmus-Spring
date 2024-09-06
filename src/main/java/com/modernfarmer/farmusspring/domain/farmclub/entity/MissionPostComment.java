package com.modernfarmer.farmusspring.domain.farmclub.entity;

import com.modernfarmer.farmusspring.domain.user.entity.User;
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
@Entity(name = "mission_post_comment")
public class MissionPostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_post_id")
    private MissionPost missionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "missionPostComment", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionPostCommentReport> missionPostCommentReports = new ArrayList<>();

    public static MissionPostComment createMissionPostComment(String comment, MissionPost missionPost, User user){
        MissionPostComment newMissionPostComment = MissionPostComment.builder()
                .comment(comment)
                .missionPost(missionPost)
                .user(user)
                .build();

        missionPost.addComment(newMissionPostComment);
        user.addMissionPostComment(newMissionPostComment);

        return newMissionPostComment;
    }

    public void addReport(MissionPostCommentReport missionPostCommentReport){
        this.missionPostCommentReports.add(missionPostCommentReport);
    }
}
