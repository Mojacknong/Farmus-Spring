package com.modernfarmer.farmusspring.domain.auth.util.social;

import com.modernfarmer.farmusspring.domain.auth.dto.LoginResponseDto;
import com.modernfarmer.farmusspring.domain.auth.repository.RedisManager;
import com.modernfarmer.farmusspring.domain.auth.util.social.dto.SocialUserResponseDto;
import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserException;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import com.modernfarmer.farmusspring.global.common.security.JwtTokenProvider;
import com.modernfarmer.farmusspring.global.response.BaseResponseDto;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Component
abstract public class SocialLogin {

    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisManager redisManager;
    private final UserRepository userRepository;


    public abstract LoginResponseDto loginMethod(String socialToken);

    public  LoginResponseDto login(SocialUserResponseDto socialUserData){

        verifySocialUserData(socialUserData);

        userRepository.findByUserNumber(String.valueOf(socialUserData.getId()))
                .orElseGet(() -> {

                    socialSignUp(socialUserData);
                    return null;
                });

        Optional<User> userLoginData = Optional.ofNullable(userRepository.findByUserNumber(String.valueOf(socialUserData.getId())).orElseThrow(() -> new UserException("해당 유저의 정보가 존재하지 않습니다.")));

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());
        String accessToken = jwtTokenProvider.createAccessToken(
                userLoginData.get().getId(),
                String.valueOf(userLoginData.get().getRole()));

        redisManager.setValueByKey(String.valueOf(userLoginData.get().getId()), refreshToken);

        return LoginResponseDto.of(
                        accessToken,
                        refreshToken,
                        userLoginData.get().isEarly()
        );
    }

    private  <T> void verifySocialUserData(T data){

        if(data == null){

            BaseResponseDto.of(UserErrorCode.NOT_FOUND_USER, "요청한 소셜 유저 정보가 없습니다.");
        }
    }

    private  <T extends SocialUserResponseDto> void socialSignUp(T socialUserData) {

        User user = User.createUser(
                "USER",
                String.valueOf(socialUserData.getId()),
                true
        );
        userRepository.save(user);

    }

    public  <T> T  getUserData(String accessToken, String apiUrl, Class<T> responseType){

        Mono<T> userInfoMono = webClient
                                    .get()
                                    .uri(apiUrl)
                                    .headers(headers -> headers.set("Authorization", accessToken))
                                    .retrieve()
                                    .bodyToMono(responseType);

        return userInfoMono.block();
    }


}
