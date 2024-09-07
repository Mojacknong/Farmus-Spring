package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
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
@Entity(name = "diary_comment_report")
public class DiaryCommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_comment_report_id")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_comment_id")
    private DiaryComment diaryComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static DiaryCommentReport createDiaryReport(DiaryComment diaryComment, User user, String reason){
        DiaryCommentReport report = DiaryCommentReport.builder()
                .diaryComment(diaryComment)
                .reason(reason)
                .user(user)
                .build();

        diaryComment.addReport(report);
        return report;
    }
}
