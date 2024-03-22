package com.modernfarmer.farmusspring.domain.auth.service;


import com.modernfarmer.farmusspring.domain.auth.dto.TokenResponseDto;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    private final UserRepository userRepository;

    private final SocialLogin socialLogin;

    public BaseResponseDto<TokenResponseDto> googleLogin(String googleAccessToken) {

        GoogleUserResponseDto  googleUserData = socialLogin.getUserData(
                googleAccessToken,
                "https://www.googleapis.com/oauth2/v2/userinfo",
                GoogleUserResponseDto.class);

        verifySocialUserData(googleUserData);

        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(googleUserData.getId()));

        verifyUserDataAndSignUp(userData, googleUserData);

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(googleUserData.getId()));

        verifySocialUserData(userLoginData);

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());
        String accessToken = jwtTokenProvider.createAccessToken(
                userLoginData.get().getId(),
                String.valueOf(userLoginData.get().getRole()));

        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()), refreshToken);

        log.info("구글 로그인 완료");

        return BaseResponseDto.of(
                SuccessCode.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(
                                userLoginData.get().getId(),
                                String.valueOf(userLoginData.get().getRole())),
                        refreshToken,
                        userLoginData.get().isEarly()
                )
        );
    }



    public BaseResponseDto<TokenResponseDto> kakaoLogin(String kakaoAccessToken) {

        KakaoUserResponseDto kakaoUserData =  socialLogin.getUserData(
                kakaoAccessToken,
                "https://kapi.kakao.com/v2/user/me",
                KakaoUserResponseDto.class);

        verifySocialUserData(kakaoUserData);

        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(kakaoUserData.getId()));

        verifyUserDataAndSignUp(userData, kakaoUserData);

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(kakaoUserData.getId()));

        verifySocialUserData(userLoginData);

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());
        String accessToken = jwtTokenProvider.createAccessToken(
                userLoginData.get().getId(),
                String.valueOf(userLoginData.get().getRole()));


        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()), refreshToken);

        log.info("카카오 로그인 완료");

        return BaseResponseDto.of(
                SuccessCode.SUCCESS,
                TokenResponseDto.of(
                        accessToken,
                        refreshToken,
                        userLoginData.get().isEarly()
                )
        );
    }

    public <T> BaseResponseDto verifySocialUserData(T data){

        if(data == null){

            return BaseResponseDto.of(ErrorCode.BAD_REQUEST, null);

        }
        return null;
    }


    public <T extends SocialUserResponseDto> void verifyUserDataAndSignUp(Optional<User> data, T socialUserData) {
        User user;

        if (data.isEmpty()) {
            user = User.createUser(
                    "USER",
                    String.valueOf(socialUserData.getId()),
                    true
            );

            userRepository.save(user);
        }
    }




    }