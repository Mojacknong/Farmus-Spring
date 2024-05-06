package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.CommentDelete;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.CommentWrite;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request.LikePress;
import com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response.*;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.DiaryLike;
import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieDiaryService;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieGardenService;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto<Void> settingMyVeggieDiary(
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam("content") String content,
            @RequestParam("isOpen") boolean isOpen,
            @RequestParam("state") String state,
            @RequestParam("myVeggieId") Long myVeggieId
    ) throws IOException {
        return  myVeggieDiaryService.settingMyVeggieDiary(
                multipartFile,
                content,
                isOpen,
                state,
                myVeggieId
        );
    }

    @GetMapping(value = "/{myVeggieId}/check")
    public BaseResponseDto<CheckTodayDiaryResponse> checkTodayDiary(
            @PathVariable("myVeggieId") Long myVeggieId
    )  {
        return myVeggieDiaryService.checkTodayDiary(MyVeggie.builder().id(myVeggieId).build());
    }

    @GetMapping(value = "/{myVeggieId}/one")
    public BaseResponseDto<SelectDiaryOneResponse> selectDiaryOne(
            @PathVariable("myVeggieId") Long myVeggieId
    )  {
        return myVeggieDiaryService.selectDiaryOne(MyVeggie.builder().id(myVeggieId).build());
    }

    @GetMapping(value = "/{myVeggieId}/count")
    public BaseResponseDto<MyVeggieDiaryCount> selectDiaryCount(
            @PathVariable("myVeggieId") Long myVeggieId
    )  {
        MyVeggie myVeggie = MyVeggie.builder().id(myVeggieId).build();
        MyVeggieDiaryCount result = myVeggieDiaryService.selectDiaryCount(myVeggie);

        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }

    @GetMapping(value = "/{myVeggieId}/all")
    public BaseResponseDto<MyVeggieDiaryCount> selectDiaryAll(
            @PathVariable("myVeggieId") Long myVeggieId
    )  {
        MyVeggie myVeggie = MyVeggie.builder().id(myVeggieId).build();
        List<AllDairy> result = myVeggieDiaryService.selectDiaryAll(myVeggie);

        return BaseResponseDto.of(SuccessCode.SUCCESS, result);
    }

    @PostMapping(value = "/like")
    public BaseResponseDto<?> pressLike(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody LikePress likePress)  {
        myVeggieDiaryService.pressLike(user.getUserId(), likePress.getDiaryId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
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
}
