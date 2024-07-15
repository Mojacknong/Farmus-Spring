package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.RoutineTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoutineTimeRepository extends JpaRepository<RoutineTime, Long> {

    @Query("SELECT rt FROM routine_time AS rt  WHERE rt.id = :routineTimeId AND rt.routine = :routine")
    Optional<RoutineTime> findRoutineTimeByIdAndRoutineId(@Param("routine") Routine routine, @Param("routineTimeId") Long routineTimeId);


    @Modifying
    @Query("UPDATE routine_time AS rt SET rt.complete = true WHERE rt.id = :routineTimeId AND  rt.routine = :routine")
    void updateRoutineTimeComplete(@Param("routine") Routine routine, @Param("routineTimeId") Long routineTimeId);


}
