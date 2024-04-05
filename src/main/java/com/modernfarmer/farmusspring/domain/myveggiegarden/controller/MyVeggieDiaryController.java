package com.modernfarmer.farmusspring.domain.myveggiegarden.controller;

import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieDiaryService;
import com.modernfarmer.farmusspring.domain.myveggiegarden.service.MyVeggieGardenService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-veggie/diary")
public class MyVeggieDiaryController {

    private final MyVeggieDiaryService myVeggieDiaryService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
