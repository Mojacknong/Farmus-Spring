package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MyVeggieRepository extends JpaRepository<MyVeggie, Long> {

    Optional<MyVeggie> findById(Long id);

    @Query("SELECT d FROM diary AS d WHERE d.myVeggie = :myVeggie ORDER BY d.createdDate DESC")
    List<Diary> findDiariesByMyVeggie(MyVeggie myVeggie);

    @Query("SELECT d FROM diary AS d WHERE d.myVeggie = :myVeggie AND FUNCTION('DATE', d.createdDate) = CURRENT_DATE ")
    Diary findDiariesByMyVeggieAndToday(MyVeggie myVeggie);

    @Query("SELECT mv FROM my_veggie AS mv JOIN FETCH  mv.user WHERE mv.user.id = :userId")
    List<MyVeggie> findMyVeggieUserId(@Param("userId") Long userId);


    @Query("SELECT mv FROM my_veggie AS mv LEFT JOIN  mv.userFarmClub WHERE mv.id= :myVeggieId")
    MyVeggie findMyVeggieAndFarmClub(@Param("myVeggieId") Long myVeggieId);

    @Query("SELECT r FROM routine AS r  WHERE r.myVeggie = :myVeggie ")
    List<Routine> findMyVeggieRoutineById(@Param("myVeggie") MyVeggie myVeggie);
    @Modifying
    @Query("UPDATE my_veggie  SET nickname = :nickname, birth = :birth WHERE id = :myVeggieId ")
    void updateMyVeggie(@Param("myVeggieId") Long myVeggieId,
                                 @Param("nickname") String nickanme,
                                 @Param("birth") Date birth);



    @Query("SELECT mv FROM my_veggie AS mv LEFT JOIN  mv.routines WHERE mv.user.id= :userId")
    List<MyVeggie> findMyVeggieAndRoutine(@Param("userId") Long userId);


}
