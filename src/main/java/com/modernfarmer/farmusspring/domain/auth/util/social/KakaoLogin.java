package com.modernfarmer.farmusspring.domain.auth.util.social;


import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.repository.RedisManager;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.KakaoUserResponseDto;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.common.security.JwtTokenProvider;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoLogin extends SocialLogin {

    public KakaoLogin(WebClient webClient, JwtTokenProvider jwtTokenProvider, RedisManager redisManager, UserRepository userRepository) {
        super(webClient, jwtTokenProvider, redisManager, userRepository);
    }

    @Override
    public LoginResponseDto loginMethod(String socialToken) {
        KakaoUserResponseDto socialUserData = getUserData(
                socialToken,
                "https://kapi.kakao.com/v2/user/me",
                KakaoUserResponseDto.class);


        return login(socialUserData);
    }
}
