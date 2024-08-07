package com.modernfarmer.farmusspring.domain.history.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.history.dto.res.*;
import com.modernfarmer.farmusspring.domain.history.service.HistoryService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "히스토리 생성 성공")
    public BaseResponseDto<?> createHistory(
            @AuthenticationPrincipal CustomUser user
    ) {
        historyService.createHistory(user.getUserId());
        return BaseResponseDto.of(SuccessCode.CREATED, null);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HistoryResponseDto.class)))
    public BaseResponseDto<?> getUserHistory(
            @PathVariable Long id
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, historyService.getUserHistory(id));
    }

    @GetMapping("/farmclub")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FarmClubHistoryListResponseDto.class)))
    public BaseResponseDto<?> getFarmClubHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, historyService.getFarmClubHistories(user.getUserId()));
    }

    @GetMapping("/farmclub/{detailId}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FarmClubHistoryDetailResponseDto.class)))
    public BaseResponseDto<?> getFarmClubHistoryDetail(
            @PathVariable String detailId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, historyService.getFarmClubHistoryDetail(detailId));
    }

    @GetMapping("/veggie")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VeggieHistoryListResponseDto.class)))
    public BaseResponseDto<?> getVeggieHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, historyService.getVeggieHistories(user.getUserId()));
    }

    @GetMapping("/veggie/{detailId}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VeggieHistoryDetailResponseDto.class)))
    public BaseResponseDto<?> getVeggieHistoryDetail(
            @PathVariable String detailId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, historyService.getVeggieHistoryDetail(detailId));
    }
}
