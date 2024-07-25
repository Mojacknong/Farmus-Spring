package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
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
public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Query("SELECT r FROM routine AS r  WHERE r.id = :routineId ")
    Optional<Routine> findRoutineById(@Param("routineId") Long routineId);


    @Modifying
    @Query("UPDATE routine AS r SET r.content = :content, r.period = :period WHERE r.id = :routineId")
    void updateRoutine(@Param("routineId") Long routineId, @Param("content") String content, @Param("period") int period);

    @Modifying
    @Query("DELETE FROM routine AS r WHERE r.id = :routineId")
    void deleteRoutine(@Param("routineId") Long routineId);


    @Modifying
    @Query("UPDATE routine AS r SET r.complete = true WHERE   r.id = :routineId")
    void updateRoutineComplete(@Param("routine") Routine routine, @Param("routineId") Long routineId);

    @Query("SELECT r FROM routine AS r " +
            "JOIN FETCH r.myVeggie AS mv " +
            "WHERE mv.user = :user AND " +
            "FUNCTION('YEAR', r.date) = FUNCTION('YEAR', :month) AND FUNCTION('MONTH', r.date) = FUNCTION('MONTH', :month)")
    List<Routine> findRoutineAndRoutineAndMyVeggieByMonthWithUser(@Param("month") Date month, @Param("user") User user );

}