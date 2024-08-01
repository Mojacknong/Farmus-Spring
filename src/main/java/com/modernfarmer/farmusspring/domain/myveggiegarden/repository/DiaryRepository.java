package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Modifying
    @Query("DELETE FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    void deleteDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);

    @Modifying
    @Query("DELETE FROM diary AS d WHERE d.id = :diaryId")
    void deleteDiaryById(@Param("diaryId") Long diaryId);


    @Query("SELECT d FROM diary AS d WHERE d.id = :diaryId AND d.myVeggie.id = :myVeggieId")
    Optional<Diary> findDiaryByIdAndMyVeggieId(@Param("diaryId") Long diaryId, @Param("myVeggieId") Long myVeggieId);

    @Query("SELECT dl FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    DiaryLike findDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);


     @Query("SELECT dc FROM diary_comment AS dc " +
             "JOIN FETCH dc.diary AS d " +
             "JOIN FETCH d.myVeggie AS mv " +
             "JOIN FETCH mv.user " +
             "WHERE d.id = :diaryId")
     List<DiaryComment> findDiaryById(@Param("diaryId") Long diaryId);


    @Query("SELECT new com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary(d, " +
            "CASE WHEN dl.user.id = :userId THEN true ELSE false END) " +
            "FROM diary AS d " +
            "JOIN FETCH d.myVeggie AS mv " +
            "JOIN FETCH mv.user " +
            "LEFT JOIN diary_comment  AS dc ON dc.id = d.id "+
            "LEFT JOIN  diary_like  AS dl ON dl.id = d.id " +
            "WHERE d.farmClub.id = :farmClubId AND d.isOpen = true "
          )
    List<SortedMyLikeDiary> findDiaryByFarmClub(@Param("farmClubId") Long farmClubId, @Param("userId") Long userId);

    @Query("SELECT new com.modernfarmer.farmusspring.domain.myveggiegarden.dto.SortedMyLikeDiary(d, " +
            "CASE WHEN dl.user.id = :userId THEN true ELSE false END) " +
            "FROM diary AS d " +
            "JOIN FETCH d.myVeggie AS mv " +
            "JOIN FETCH mv.user " +
            "LEFT JOIN diary_like AS dl ON dl.diary.id = d.id AND dl.user.id = :userId " +
            "WHERE d.myVeggie = :myVeggie " +
            "ORDER BY d.createdDate DESC")
    List<SortedMyLikeDiary> findDiariesByMyVeggie(@Param("myVeggie") MyVeggie myVeggie, @Param("userId") Long userId);

    @Query("SELECT d FROM diary AS d WHERE d.myVeggie = :myVeggie AND FUNCTION('DATE', d.createdDate) = CURRENT_DATE")
    List<Diary> findDiaryByToday(@Param("myVeggie") MyVeggie myVeggie);






}
