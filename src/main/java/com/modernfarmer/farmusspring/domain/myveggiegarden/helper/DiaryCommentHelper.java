package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;


import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiaryCommentHelper {

    private final DiaryCommentRepository diaryCommentRepository;

    public void deleteComments(Long userId){
        diaryCommentRepository.deleteDiaryCommentsByUserId(userId);
    }
}
