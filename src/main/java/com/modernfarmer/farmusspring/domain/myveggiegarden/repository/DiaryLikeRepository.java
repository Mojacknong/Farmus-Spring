package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {

    @Query("SELECT count(dl) FROM diary_like as dl WHERE dl.diary.id = :diaryId ")
    int findDiaryLikeCountById(@Param("diaryId") Long diaryId);

}
