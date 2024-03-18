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
@Entity(name = "routine")
public class Routine extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "content")
    private String content;

    @Column(name = "period")
    private int period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_veggie_id")
    private MyVeggie myVeggie;


    public static Routine createRoutine(Date date, String content, int period, MyVeggie myVeggie){
        Routine newRoutine = Routine.builder()
                .date(date)
                .content(content)
                .period(period)
                .myVeggie(myVeggie)
                .build();

        myVeggie.addRoutine(newRoutine);
        return newRoutine;

    }
}
