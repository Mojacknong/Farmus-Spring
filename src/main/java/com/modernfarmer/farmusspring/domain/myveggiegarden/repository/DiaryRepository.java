package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Modifying
    @Query("DELETE FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    void deleteDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);


    @Query("SELECT dl FROM diary_like AS dl WHERE dl.diary = :diary AND dl.user = :user")
    DiaryLike findDiaryLikeByIdAndUser(@Param("user") User user, @Param("diary") Diary diary);

}
