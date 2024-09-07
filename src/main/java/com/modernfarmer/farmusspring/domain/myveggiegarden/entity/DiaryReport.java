package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;


import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostComment;
import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPostCommentReport;
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
@Entity(name = "diary_report")
public class DiaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_report_id")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static DiaryReport createDiaryReport(Diary diary, User user, String reason){
        DiaryReport report = DiaryReport.builder()
                .diary(diary)
                .reason(reason)
                .user(user)
                .build();

        diary.addReport(report);
        return report;
    }
}
