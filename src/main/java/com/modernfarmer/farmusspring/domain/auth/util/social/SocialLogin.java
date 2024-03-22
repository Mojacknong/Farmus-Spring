package com.modernfarmer.farmusspring.domain.auth.util.social;

import com.modernfarmer.farmusspring.domain.auth.util.social.dto.GoogleUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Component
public class SocialLogin {

    private final WebClient webClient;


    public <T> T  getUserData(String accessToken, String apiUrl, Class<T> responseType){

        Mono<T> userInfoMono = webClient
                                    .get()
                                    .uri(apiUrl)
                                    .headers(headers -> headers.set("Authorization", accessToken))
                                    .retrieve()
                                    .bodyToMono(responseType);

        return userInfoMono.block();
    }

}
