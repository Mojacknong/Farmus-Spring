package com.modernfarmer.farmusspring.domain.auth.util.social;

import com.modernfarmer.farmusspring.domain.auth.repository.RedisManager;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.GoogleUserResponseDto;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.KakaoUserResponseDto;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.common.security.JwtTokenProvider;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleLogin extends SocialLogin{

    public GoogleLogin(WebClient webClient, JwtTokenProvider jwtTokenProvider, RedisManager redisManager, UserRepository userRepository) {
        super(webClient, jwtTokenProvider, redisManager, userRepository);
    }

    @Override
    public BaseResponseDto loginMethod(String socialToken) {
        GoogleUserResponseDto socialUserData = getUserData(
                socialToken,
                "https://www.googleapis.com/oauth2/v2/userinfo",
                GoogleUserResponseDto.class);


        return login(socialUserData);
    }

}
