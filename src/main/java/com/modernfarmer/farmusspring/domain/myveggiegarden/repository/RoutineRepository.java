package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
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

    @Query("SELECT r FROM routine AS r  JOIN FETCH r.routineTimes rt WHERE r.myVeggie = :myVeggie AND rt.date = :date")
    List<Routine> findRoutineByMyVeggieWithDate(@Param("myVeggie")MyVeggie myVeggie, @Param("date") Date date);
    @Modifying
    @Query("UPDATE routine AS r SET r.content = :content, r.period = :period WHERE r.id = :routineId")
    void updateRoutine(@Param("routineId") Long routineId, @Param("content") String content, @Param("period") int period);

    @Modifying
    @Query("DELETE FROM routine AS r WHERE r.id = :routineId")
    void deleteRoutine(@Param("routineId") Long routineId);
}
