package com.modernfarmer.farmusspring.domain.auth.service;


import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.dto.TokenResponseDto;
import com.modernfarmer.farmusspring.domain.auth.exception.AuthErrorCode;
import com.modernfarmer.farmusspring.domain.auth.exception.AuthExceptionHandler;
import com.modernfarmer.farmusspring.domain.auth.exception.AuthRefreshTokenValidateException;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public BaseResponseDto<LoginResponseDto> googleLogin(String googleAccessToken) {

        return BaseResponseDto.of(SuccessCode.SUCCESS,
                googleLogin.loginMethod(googleAccessToken));
    }
    @Transactional
    public BaseResponseDto<LoginResponseDto> kakaoLogin(String kakaoAccessToken) {

        return BaseResponseDto.of(SuccessCode.SUCCESS,
                kakaoLogin.loginMethod(kakaoAccessToken));


    }
    @Transactional
    public BaseResponseDto<Void> logout(Long userId) {
        deleteredisToken(userId);
        log.info("로그아웃 완료");
        return BaseResponseDto.of(SuccessCode.SUCCESS,null);
    }

    @Transactional
    public BaseResponseDto<TokenResponseDto> reissueToken(Long userId, String refreshToken) {

        validateRefreshToken(userId, refreshToken);
        User user = findUser(userId);
        return BaseResponseDto.of(SuccessCode.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(Long.valueOf(userId), user.getRole()),
                        refreshToken));
    }

    private void deleteredisToken(Long userId){
        redisManager.deleteValueByKey(String.valueOf(userId));
    }
    private String getRedisToken(Long key){return redisManager.getValueByKey(key);}


    private User findUser(Long userId) {
        return userRepository.findUserData(Long.valueOf(userId));
    }


    private void validateRefreshToken(Long userId, String refreshToken) {
        String redisRefreshToken = getRedisToken(userId);
        if (!refreshToken.equals(redisRefreshToken)) {
            throw new AuthRefreshTokenValidateException("일치하지 않는 토큰입니다.");
        }
    }
}