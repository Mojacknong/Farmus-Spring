package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.Diary;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryComment;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieDiaryService;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-veggie/diary")
public class MyVeggieDiaryController {

    private final MyVeggieDiaryService myVeggieDiaryService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto<Void> settingMyVeggieDiary(
            @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            @RequestPart MyVeggieDiaryInsert myVeggieDiaryInsert
            ) throws IOException {
        return  myVeggieDiaryService.settingMyVeggieDiary(
                multipartFile,
                myVeggieDiaryInsert.getContent(),
                myVeggieDiaryInsert.isOpen(),
                myVeggieDiaryInsert.getState(),
                myVeggieDiaryInsert.getMyVeggieId()
        );
    }


    @GetMapping(value = "/{farmClubId}")
    public BaseResponseDto<FarmClubDiary> findFarmClubDiary(@PathVariable("farmClubId") Long farmClubId, @AuthenticationPrincipal CustomUser user){
        List<FarmClubDiary> farmClubDiaryList = myVeggieDiaryService.findDiaryAccordingToFarmClub(farmClubId, user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubDiaryList);
    }

    @DeleteMapping()
    public BaseResponseDto<?> eraseDiary(
            @Validated @RequestBody DiaryDeleteDto diaryDeleteDto,
            @AuthenticationPrincipal CustomUser user
    ){
        myVeggieDiaryService.eraseDiary(diaryDeleteDto, user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }



    @GetMapping(value = "/{myVeggieId}/check")
    public BaseResponseDto<CheckTodayDiaryResponse> checkTodayDiary(@PathVariable("myVeggieId") Long myVeggieId)  {
        return myVeggieDiaryService.checkTodayDiary(MyVeggie.builder().id(myVeggieId).build());
    }

    @GetMapping(value = "/{myVeggieId}/one")
    public BaseResponseDto<SelectDiaryOneResponse> selectDiaryOne(@PathVariable("myVeggieId") Long myVeggieId) {
        return myVeggieDiaryService.selectDiaryOne(MyVeggie.builder().id(myVeggieId).build());
    }

    @GetMapping(value = "/{myVeggieId}/count")
    public BaseResponseDto<MyVeggieDiaryCount> selectDiaryCount(@PathVariable("myVeggieId") Long myVeggieId)  {
        MyVeggie myVeggie = MyVeggie.builder().id(myVeggieId).build();
        MyVeggieDiaryCount result = myVeggieDiaryService.selectDiaryCount(myVeggie);
        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }

    @GetMapping(value = "/{myVeggieId}/all")
    public BaseResponseDto<MyVeggieDiaryCount> selectDiaryAll(
            @PathVariable("myVeggieId") Long myVeggieId,
            @AuthenticationPrincipal CustomUser user
    )  {
        MyVeggie myVeggie = MyVeggie.builder().id(myVeggieId).build();
        List<AllDairy> result = myVeggieDiaryService.selectDiaryAll(myVeggie, user.getUserId());

        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }

    @PostMapping(value = "/like")
    public BaseResponseDto<?> pressLike(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody Like like)  {
        myVeggieDiaryService.pressLike(user.getUserId(), like.getDiaryId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @DeleteMapping(value = "/like")
    public BaseResponseDto<?> cancelLike(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody Like like)  {
        User userObject = User.builder().id(user.getUserId()).build();
        Diary diaryObject = Diary.builder().id(like.getDiaryId()).build();
        myVeggieDiaryService.cancelLike(userObject, diaryObject);
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping(value = "/{diaryId}/comment")
    public BaseResponseDto<?> selectComment(@AuthenticationPrincipal CustomUser user, @PathVariable("diaryId") Long diaryId)  {
        List<DiaryCommentContent> diaryCommentList = myVeggieDiaryService.selectComment(user.getUserId(), diaryId);
        return BaseResponseDto.of(SuccessCode.SUCCESS, diaryCommentList);
    }


    @PostMapping(value = "/comment")
    public BaseResponseDto<?> writeComment(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody CommentWrite commentWrite)  {
        myVeggieDiaryService.writeComment(user.getUserId(), commentWrite.getDiaryId(), commentWrite.getContent());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }


    @DeleteMapping(value = "/comment")
    public BaseResponseDto<?> deleteComment(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody CommentDelete commentDelete)  {
        User userObject = User.builder().id(user.getUserId()).build();
        myVeggieDiaryService.deleteComment(userObject, commentDelete.getDiaryCommentId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @PutMapping(value = "/comment")
    public BaseResponseDto<?> updateComment(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody CommentUpdate commentUpdate)  {
        User userObject = User.builder().id(user.getUserId()).build();
        myVeggieDiaryService.updateComment(userObject, commentUpdate.getDiaryCommentId(), commentUpdate.getContent());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }
}
