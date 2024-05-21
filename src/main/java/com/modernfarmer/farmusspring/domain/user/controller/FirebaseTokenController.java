package com.modernfarmer.farmusspring.domain.user.controller;


import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.user.dto.request.FirebaseToken;
import com.modernfarmer.farmusspring.domain.user.dto.response.UserProfileResponse;
import com.modernfarmer.farmusspring.domain.user.service.FirebaseTokenService;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class FirebaseTokenController {

    private final FirebaseTokenService firebaseTokenService;

    @DeleteMapping(value = "/firebase-token")
    public BaseResponseDto<?> deleteFirebaseToken(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody FirebaseToken firebaseToken){
        firebaseTokenService.deleteFirebaseToken(user.getUserId(), firebaseToken.getFirebaseToken());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }



    @PostMapping(value = "/firebase-token")
    public BaseResponseDto<?> addFirebaseToken(
            @AuthenticationPrincipal CustomUser user,
            @Validated @RequestBody FirebaseToken firebaseToken){
        firebaseTokenService.addFirebaseToken(user.getUserId(), firebaseToken.getFirebaseToken());
        return BaseResponseDto.of(SuccessCode.SUCCESS, null);
    }
}
