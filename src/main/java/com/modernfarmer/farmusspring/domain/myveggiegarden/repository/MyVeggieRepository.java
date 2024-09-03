package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Routine;
import com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo;
import com.modernfarmer.farmusspring.domain.user.entity.User;
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

    boolean existsByUserId(Long userId);

    Optional<MyVeggie> findById(Long id);

    @Query("SELECT d FROM diary AS d WHERE d.id = :diaryId ")
    Diary findDiaryById(@Param("diaryId") Long diaryId);

    @Query("SELECT mv FROM my_veggie AS mv WHERE mv.id = :myVeggieId AND  mv.user.id = :userId")
    Optional<MyVeggie> findMyVeggieByIdAndUserId(@Param("myVeggieId") Long myVeggieId, @Param("userId") Long userId);

    @Query("SELECT d FROM diary AS d WHERE d.myVeggie = :myVeggie ORDER BY d.createdDate DESC")
    List<Diary> findDiariesByMyVeggie(MyVeggie myVeggie);

    @Query("SELECT d FROM diary AS d WHERE d.myVeggie = :myVeggie AND FUNCTION('DATE', d.createdDate) = CURRENT_DATE ")
    Diary findDiariesByMyVeggieAndToday(MyVeggie myVeggie);

    @Query("SELECT mv FROM my_veggie AS mv JOIN FETCH  mv.user WHERE mv.user.id = :userId")
    List<MyVeggie> findMyVeggieUserId(@Param("userId") Long userId);


    @Query("SELECT mv FROM my_veggie AS mv LEFT JOIN  mv.userFarmClub WHERE mv.id= :myVeggieId")
    MyVeggie findMyVeggieAndFarmClub(@Param("myVeggieId") Long myVeggieId);

    @Modifying
    @Query("UPDATE my_veggie  SET nickname = :nickname, birth = :birth WHERE id = :myVeggieId ")
    void updateMyVeggie(@Param("myVeggieId") Long myVeggieId,
                                 @Param("nickname") String nickanme,
                                 @Param("birth") Date birth);

    @Query("SELECT mv FROM my_veggie AS mv LEFT JOIN  mv.routines WHERE mv.user.id= :userId")
    List<MyVeggie> findMyVeggieAndRoutine(@Param("userId") Long userId);


    @Query("SELECT dc FROM diary_comment AS dc WHERE dc.id = :diaryCommentId AND dc.user = :user")
    Optional<DiaryComment> findDiaryCommentByIdAndUserId(@Param("diaryCommentId") Long diaryCommentId, @Param("user") User user);

    @Query("SELECT new com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo(mv.id, mv.veggieInfoId, mv.nickname) FROM my_veggie mv WHERE mv.user.id = :userId AND mv.veggieInfoId = :veggieInfoId")
    Optional<MyVeggieVo> findMyVeggieInfo(Long userId, String veggieInfoId);

    @Query("SELECT new com.modernfarmer.farmusspring.domain.myveggiegarden.vo.MyVeggieVo(mv.id, mv.veggieInfoId, mv.nickname) FROM my_veggie mv WHERE mv.user.id = :userId AND mv.userFarmClub IS NULL")
    List<MyVeggieVo> findMyVeggieInfoForCreate(Long userId);

    @Modifying
    @Query("DELETE FROM diary_comment AS dc WHERE dc.id = :diaryCommentId AND dc.user = :user")
    void deleteDiaryCommentByIdAndUserId(@Param("diaryCommentId") Long diaryCommentId, @Param("user") User user);

    @Modifying
    @Query("UPDATE diary_comment  AS dc SET dc.comment = :content WHERE dc.id = :diaryCommentId AND dc.user = :user")
    void updateDiaryCommentByIdAndUserId(@Param("diaryCommentId") Long diaryCommentId, @Param("user") User user, @Param("content") String content);
    @Modifying
    @Query("UPDATE routine AS r SET r.date = :date WHERE r.id = :routineId")
    void updateRoutinePeriod(@Param("routineId") Long routineId, @Param("date") Date date);

    @Modifying
    @Query("delete from my_veggie  as mv where mv.user.id = :userId")
    void deleteMyVeggiesByUserId(@Param("userId") Long userId);

    @Query("SELECT mv FROM my_veggie AS mv " +
            "LEFT JOIN  mv.routines AS r " +
            "WHERE mv.user= :user "
            )
    List<MyVeggie> findMyVeggieAndRoutineByUserWithDate(@Param("user") User user);





}
