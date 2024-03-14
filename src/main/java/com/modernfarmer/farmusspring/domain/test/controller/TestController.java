package com.modernfarmer.farmusspring.domain.test.controller;

import com.modernfarmer.farmusspring.domain.test.service.TestService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @GetMapping
    public BaseResponseDto<?> test(@RequestParam Boolean flag) {
        return BaseResponseDto.of(SuccessCode.SUCCESS, testService.test(flag));
    }
}
