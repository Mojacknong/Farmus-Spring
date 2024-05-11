package com.modernfarmer.farmusspring.domain.history.controller;

import com.modernfarmer.farmusspring.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{id}")
    public void getUserHistory(
            @PathVariable Long id
    ) {
        return ;
    }

    @GetMapping("/farmclub/{id}")
    public void getFarmClubHistory(
            @PathVariable Long id
    ) {
        return ;
    }

    @GetMapping("/veggie/{id}")
    public void getVeggieHistory(
            @PathVariable Long id
    ) {
        return ;
    }
}
