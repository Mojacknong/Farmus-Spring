package com.modernfarmer.farmusspring.domain.myveggiegarden.helper;

import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.DiaryNotFoundException;
import com.modernfarmer.farmusspring.domain.myveggiegarden.exception.MyVeggieGardenErrorCode;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryCommentRepository;
import com.modernfarmer.farmusspring.domain.myveggiegarden.repository.DiaryRepository;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;
import com.modernfarmer.farmusspring.domain.user.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiaryHelper {

    private final DiaryRepository diaryRepository;

    public Diary getDiaryEntity(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다.", MyVeggieGardenErrorCode.NOT_FOUND_DIARY));
    }
}
