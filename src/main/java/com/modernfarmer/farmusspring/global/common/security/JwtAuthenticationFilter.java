package com.modernfarmer.farmusspring.global.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;



    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) {
        try {
            String token = jwtTokenProvider.getToken(servletRequest);

            if (token != null) {

                jwtTokenProvider.validateToken(token);
                log.info(String.valueOf(1));

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                log.info(String.valueOf(2));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(String.valueOf(3));
            }
        }catch (IllegalArgumentException e) {
            // 예외 처리: Invalid access token header
            setErrorResponse(servletRequest, servletResponse, "유효하지 않은 토큰 헤더", 400);
            return;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            setErrorResponse(servletRequest, servletResponse, "검증 에러 ",421);
            return;
        } catch (ExpiredJwtException e) {
            setErrorResponse(servletRequest, servletResponse, "토큰 만료",419);
            return;
        } catch (JwtException e) {
            setErrorResponse(servletRequest, servletResponse, "jwt 에러",420);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, String message,
                                 int code) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final Map<String, Object> body = new HashMap<>();

        body.put("message", message);
        body.put("code", code);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_OK);


    }


}