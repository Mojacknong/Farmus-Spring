package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.RoutineTime;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineTimeRepository extends JpaRepository<RoutineTime, Long> {

    @Query("SELECT rt FROM routine_time AS rt  WHERE rt.id = :routineTimeId AND rt.routine = :routine")
    Optional<RoutineTime> findRoutineTimeByIdAndRoutineId(@Param("routine") Routine routine, @Param("routineTimeId") Long routineTimeId);


    @Modifying
    @Query("UPDATE routine_time AS rt SET rt.complete = true WHERE rt.id = :routineTimeId AND  rt.routine = :routine")
    void updateRoutineTimeComplete(@Param("routine") Routine routine, @Param("routineTimeId") Long routineTimeId);



    @Query("SELECT rt FROM routine_time AS rt " +
            "JOIN FETCH  rt.routine AS r " +
            "JOIN FETCH r.myVeggie AS mv " +
            "WHERE mv.user = :user AND " +
            "FUNCTION('YEAR', rt.date) = FUNCTION('YEAR', :month) AND FUNCTION('MONTH', rt.date) = FUNCTION('MONTH', :month)")
    List<RoutineTime> findRoutineTimeAndRoutineAndMyVeggieByMonthWithUser(@Param("month") Date month, @Param("user") User user );

    @Query("SELECT rt FROM routine_time AS rt WHERE rt.date = :date AND rt.routine = :routine" )
    RoutineTime findRoutineTimeByRoutineAndDate(@Param("routine") Routine routine,@Param("date") Date date);



}
