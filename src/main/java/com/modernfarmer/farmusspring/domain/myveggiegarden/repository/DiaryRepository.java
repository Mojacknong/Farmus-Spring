package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Modifying
    @Query("DELETE FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    void deleteDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);


    @Query("SELECT dl FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    DiaryLike findDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);


     @Query("SELECT dc FROM diary_comment AS dc " +
             "JOIN FETCH dc.diary AS d " +
             "JOIN FETCH d.myVeggie AS mv " +
             "JOIN FETCH mv.user " +
             "JOIN FETCH d.farmClub " +
             "WHERE d.id = :diaryId AND d.farmClub.id = :farmClubId AND d.isOpen = true")
     List<DiaryComment> findDiary(@Param("diaryId") Long diaryId, @Param("farmClubId") Long farmClubId);


    @Query("SELECT d FROM diary AS d " +
            "JOIN FETCH d.myVeggie AS mv " +
            "JOIN FETCH mv.user " +
            "LEFT JOIN diary_comment  AS dc ON dc.id = d.id "+
            "LEFT JOIN  diary_like  AS dl ON dl.id = d.id " +
            "WHERE d.farmClub.id = :farmClubId AND d.isOpen = true "
          )
    List<Diary> findDiaryByFarmClub(@Param("farmClubId") Long farmClubId);

}
