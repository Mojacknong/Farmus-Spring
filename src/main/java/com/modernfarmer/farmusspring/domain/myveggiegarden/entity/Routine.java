package com.modernfarmer.farmusspring.domain.myveggiegarden.entity;


import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    @Column(name = "date")
//    private Date date;

    @Column(name = "content")
    private String content;

    @Column(name = "period")
    private int period;

//    @Column(name = "compete")
//    private boolean complete;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_veggie_id")
    private MyVeggie myVeggie;


    @OneToMany(mappedBy = "routine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<RoutineTime> routineTimes = new ArrayList<>();

    public static Routine createRoutine(String content, int period, MyVeggie myVeggie){
        Routine newRoutine = Routine.builder()
                .content(content)
                .period(period)
                .myVeggie(myVeggie)
                .build();
        myVeggie.addRoutine(newRoutine);
        return newRoutine;
    }

    public void addRoutineTime(RoutineTime routineTime) {
        routineTimes.add(routineTime);
    }
}
