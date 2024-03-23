package com.modernfarmer.farmusspring.domain.auth.service;


import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.dto.TokenResponseDto;
import com.modernfarmer.farmusspring.domain.auth.repository.RedisManager;
import com.modernfarmer.farmusspring.domain.auth.util.social.GoogleLogin;
import com.modernfarmer.farmusspring.domain.auth.util.social.KakaoLogin;
import com.modernfarmer.farmusspring.domain.auth.util.social.SocialLogin;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.GoogleUserResponseDto;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.KakaoUserResponseDto;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.SocialUserResponseDto;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.common.security.JwtTokenProvider;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.global.response.ErrorCode;
import com.modernfarmer.farmusspring.global.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisManager redisManager;

    private final UserRepository userRepository;

    private final GoogleLogin googleLogin;

    private final KakaoLogin kakaoLogin;

    public BaseResponseDto<LoginResponseDto> googleLogin(String googleAccessToken) {

        return BaseResponseDto.of(SuccessCode.SUCCESS,
                googleLogin.loginMethod(googleAccessToken)

                );
    }

    public BaseResponseDto<LoginResponseDto> kakaoLogin(String kakaoAccessToken) {

        return BaseResponseDto.of(SuccessCode.SUCCESS,
                kakaoLogin.loginMethod(kakaoAccessToken)
        );


    }

    public BaseResponseDto<Void> logout(Long userId) {

        redisManager.deleteValueByKey(String.valueOf(userId));
        log.info("로그아웃 완료");
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    public BaseResponseDto<TokenResponseDto> reissueToken(Long userId, String refreshToken) {

        validateRefreshToken(userId, refreshToken);
        User user = userRepository.findUserData(Long.valueOf(userId));
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(Long.valueOf(userId), user.getRole()),
                        refreshToken
                ));
    }


    private BaseResponseDto validateRefreshToken(Long userId, String refreshToken) {

        String redisRefreshToken = redisManager.getValueByKey(userId);
        if (refreshToken.equals(redisRefreshToken)) {

            return null;
        }
        return BaseResponseDto.of(ErrorCode.WRONG_TOKEN,null);
    }
}