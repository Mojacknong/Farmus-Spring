package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;

import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "routine_time")
public class RoutineTime extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_time_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE")
    private Date date;


    @Column(name = "compete")
    private boolean complete;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;


    public static RoutineTime createRoutineTime(Date date, boolean complete, Routine routine){
        RoutineTime newRoutineTime = RoutineTime.builder()
                .date(date)
                .complete(complete)
                .routine(routine)
                .build();
        routine.addRoutineTime(newRoutineTime);
        return newRoutineTime;
    }
}
