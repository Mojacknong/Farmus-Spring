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
    public void getFarmClubHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return ;
    }

    @GetMapping("/farmclub/{id}")
    public void getFarmClubHistoryDetail(
            @PathVariable Long id
    ) {
        return ;
    }

    @GetMapping("/veggie")
    public void getVeggieHistories(
            @AuthenticationPrincipal CustomUser user
    ) {
        return ;
    }

    @GetMapping("/veggie/{id}")
    public void getVeggieHistoryDetail(
            @PathVariable Long id
    ) {
        return ;
    }
}
