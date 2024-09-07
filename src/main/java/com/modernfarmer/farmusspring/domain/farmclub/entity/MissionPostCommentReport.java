package com.modernfarmer.farmusspring.domain.farmclub.entity;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "mission_post_comment_report")
public class MissionPostCommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_comment_report_id")
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_post_comment_id")
    private MissionPostComment missionPostComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static MissionPostCommentReport createMissionPostCommentReport(MissionPostComment missionPostComment, User user){
        MissionPostCommentReport report = MissionPostCommentReport.builder()
                .missionPostComment(missionPostComment)
                .user(user)
                .build();

        missionPostComment.addReport(report);
        return report;
    }
}
