package com.modernfarmer.farmusspring.domain.farmclub.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.farmclub.dto.req.*;
import com.modernfarmer.farmusspring.domain.farmclub.service.FarmClubService;
import com.modernfarmer.farmusspring.domain.farmclub.service.MissionPostService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm-club")
public class FarmClubController {

    private final FarmClubService farmClubService;
    private final MissionPostService missionPostService;

    // 요청 : 이름, 설명, 최대인원, 모집기한, 내 채소 id, 채소정보 id
    // 응답 : 팜클럽 id
    @PostMapping
    public BaseResponseDto<?> createFarmClub(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody CreateFarmClubRequestDto requestDto
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED, farmClubService.createFarmClub(requestDto, user.getUserId()));
    }

    @PostMapping("/register")
    public BaseResponseDto<?> registerFarmClub(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody RegisterFarmClubRequestDto requestDto
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED,
                farmClubService.registerFarmClub(requestDto.farmClubId(), requestDto.myVeggieId(), user.getUserId()));
    }

    @GetMapping("/search")
    public BaseResponseDto<?> searchFarmClub(
            @RequestParam(required = false) List<String> difficulties,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.searchFarmClub(difficulties, keyword, user.getUserId()));
    }

    @GetMapping("/{id}")
    public BaseResponseDto<?> getFarmClub(
            @PathVariable Long id
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getFarmClub(id));
    }

    @GetMapping("/recommend")
    public BaseResponseDto<?> getRecommendedFarmClubList(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getRecommendedFarmClubList(user.getUserId()));
    }

    @GetMapping("/me")
    public BaseResponseDto<?> getMyFarmClubList(
            @AuthenticationPrincipal CustomUser user
            ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getMyFarmClubList(user.getUserId()));
    }

    @GetMapping("/me/{farmClubId}")
    public BaseResponseDto<?> getMyFarmClub(
            @PathVariable Long farmClubId,
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getMyFarmClub(farmClubId, user.getUserId()));
    }

    @PostMapping("/mission")
    public BaseResponseDto<?> createMissionPost(
            @AuthenticationPrincipal CustomUser user,
            @RequestPart CreateMissionPostRequestDto requestDto,
            @RequestPart(value = "image") MultipartFile image
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED, missionPostService.createMissionPost(user.getUserId(), requestDto, image));
    }

    @PostMapping("/mission/comment")
    public BaseResponseDto<?> createMissionPostComment(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody CreateMissionPostCommentRequestDto requestDto
            ) {
        return BaseResponseDto.of(SuccessCode.CREATED, missionPostService.createMissionPostComment(user.getUserId(), requestDto));
    }

    @PostMapping("/mission/like/{missionPostId}")
    public BaseResponseDto<?> createMissionPostLike(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long missionPostId
    ) {
        return BaseResponseDto.of(SuccessCode.CREATED, missionPostService.createMissionPostLike(user.getUserId(), missionPostId));
    }

    @DeleteMapping("/mission/like/{missionPostId}")
    public BaseResponseDto<?> deleteMissionPostLike(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long missionPostId
    ) {
        missionPostService.deleteMissionPostLike(user.getUserId(), missionPostId);
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/{farmClubId}/mission")
    public BaseResponseDto<?> getMissionPostList(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long farmClubId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, missionPostService.getMissionPostList(user.getUserId(), farmClubId));
    }

    @GetMapping("/mission/{missionPostId}")
    public BaseResponseDto<?> getMissionPostComments(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long missionPostId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, missionPostService.getMissionPostComment(missionPostId, user.getUserId()));
    }

    @GetMapping("/my-veggie")
    public BaseResponseDto<?> getMyVeggieForRegister(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam String veggieInfoId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getMyVeggieForRegister(user.getUserId(), veggieInfoId));
    }

    @GetMapping("/my-veggie/create")
    public BaseResponseDto<?> getMyVeggieForCreate(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getMyVeggieForCreate(user.getUserId()));
    }

    @GetMapping("/{farmClubId}/help")
    public BaseResponseDto<?> getFarmClubHelp(
            @PathVariable Long farmClubId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.getHelpAll(farmClubId));
    }

    @DeleteMapping("/{farmClubId}")
    public BaseResponseDto<?> withdrawFarmClub(
            @PathVariable Long farmClubId,
            @AuthenticationPrincipal CustomUser user
    ) {
        farmClubService.withdrawFarmClub(farmClubId, user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/check")
    public BaseResponseDto<?> createFarmClubCheck(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, farmClubService.checkCreateFarmClub(user.getUserId()));
    }

    @DeleteMapping("/{farmClubId}/success")
    public BaseResponseDto<?> successFarmClub(
            @PathVariable Long farmClubId,
            @AuthenticationPrincipal CustomUser user
    ) {
        farmClubService.successFarmClub(farmClubId, user.getUserId());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }
}
