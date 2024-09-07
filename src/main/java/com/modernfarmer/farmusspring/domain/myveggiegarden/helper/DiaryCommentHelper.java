package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.DiaryNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.custom.CommentNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiaryCommentHelper {

    private final DiaryCommentRepository diaryCommentRepository;

    public DiaryComment getDiaryCommentEntity(Long diaryCommentId) {
        return diaryCommentRepository.findById(diaryCommentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY_COMMENT));
    }

    public void deleteComments(Long userId){
        diaryCommentRepository.deleteDiaryCommentsByUserId(userId);
    }
}
