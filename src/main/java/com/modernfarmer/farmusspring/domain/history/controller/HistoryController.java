package com.modernfarmer.farmusspring.domain.history.controller;

import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.history.service.HistoryService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
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

    @GetMapping("/{id}")
    public BaseResponseDto<?> getUserHistory(
            @PathVariable Long id
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS ,historyService.getUserHistory(id));
    }

    @GetMapping("/farmclub")
    public BaseResponseDto<?> getFarmClubHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/farmclub/{detailId}")
    public BaseResponseDto<?> getFarmClubHistoryDetail(
            @PathVariable Long detailId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/veggie")
    public BaseResponseDto<?> getVeggieHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }

    @GetMapping("/veggie/{detailId}")
    public BaseResponseDto<?> getVeggieHistoryDetail(
            @PathVariable Long detailId
    ) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }
}
