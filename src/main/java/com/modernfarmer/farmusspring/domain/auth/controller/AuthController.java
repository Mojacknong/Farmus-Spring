package com.modernfarmer.farmusspring.domain.auth.controller;

import com.modernfarmer.farmusspring.domain.auth.dto.TokenResponseDto;
import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import com.modernfarmer.farmusspring.domain.auth.service.AuthService;
import com.modernfarmer.farmusspring.global.common.security.JwtTokenProvider;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/kakao-login")
    public BaseResponseDto<TokenResponseDto> kakaoLogin(HttpServletRequest request)  {

        String accessToken = jwtTokenProvider.getSocialToken(request);

        return authService.kakaoLogin(accessToken);
    }


    @PostMapping(value = "/google-login")
    public BaseResponseDto<TokenResponseDto> googleLogin(HttpServletRequest request)  {

        String accessToken = jwtTokenProvider.getSocialToken(request);

        return authService.googleLogin(accessToken);
    }

    @DeleteMapping(value = "/logout")
    public BaseResponseDto<Void> logout(@AuthenticationPrincipal CustomUser user)  {

        log.info(String.valueOf(user.getUserId()));

        return authService.logout(user.getUserId());
    }
}
