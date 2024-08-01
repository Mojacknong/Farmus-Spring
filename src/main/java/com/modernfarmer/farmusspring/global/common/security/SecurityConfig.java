package com.modernfarmer.farmusspring.global.common.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public final JwtTokenProvider jwtTokenProvider;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://locahost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
// http://localhost:8080/swagger-ui/index.html
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                )
                .requestMatchers("/api/auth/kakao-login")
                .requestMatchers("/api/auth/google-login")
                .requestMatchers("/api/my-veggie/routine")
                .requestMatchers("/api/my-veggie/diary/check")
                .requestMatchers("/api/my-veggie/{myVeggieId}/profile")
        );
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
            .addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                            .requestMatchers(
                                    "/api/auth/logout",
                                    "/api/auth/reissue-token",
                                    "/api/user/on-boarding/motivation",
                                    "/api/user/on-boarding/level",
                                    "/api/user/on-boarding/complete",
                                    "/api/user",
                                    "/api/user/profile-image",
                                    "/api/user/profile",
                                    "/api/veggie-info",
                                    "/api/veggie-info/**",
                                    "api/my-veggie",
                                    "api/my-veggie/diary",
                                    "/api/my-veggie/simple-list",
                                    "/api/my-veggie/list",
                                    "/api/my-veggie/diary/{myVeggieId}/all",
                                    "/api/my-veggie/diary/{myVeggieId}/one"
                                    )
//                    .authenticated()
//                    .anyRequest().denyAll()
                    .permitAll()
            )
            .authorizeHttpRequests(request -> request.anyRequest().authenticated());
        return http.build();
    }




}


