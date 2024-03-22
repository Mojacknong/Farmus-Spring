package com.modernfarmer.farmusspring.global.common.security;


import com.modernfarmer.farmusspring.domain.auth.entity.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;



@Slf4j
@Component
public class JwtTokenProvider {




    @Value("${jwt.secret}")
    private String secretKey;


    private final long accessTokenTime = 30L * 1000 * 100; // 1달 토큰 유효


    private final long refreshTokenTime = 30L * 1000 * 2 * 1000; // 1달 토큰 유효

    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작", StandardCharsets.UTF_8);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createAccessToken(Long userId, String roles) {            // 토큰 생성

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String createRefreshToken(Long userId) {            // 토큰 생성
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String getToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        } else {
            // 예외 처리: 헤더가 없거나 "Bearer " 접두사가 없는 경우
            throw new IllegalArgumentException("Invalid access token header");
        }
    }
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        Long userId = this.getUserId(token);

        CustomUser customUser = CustomUser
                .builder()
                .userId(userId)
                .build();


        return new UsernamePasswordAuthenticationToken(customUser, "", customUser.getAuthorities());
    }

    public String getSocialToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        String tokenRole = request.getHeader("Authorization");

        return tokenRole;

    }

    public Long getUserId(String token) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .getSubject();

        Long userId = Long.valueOf(info);

        return userId;

    }

    public boolean validateToken(String token) {                         // 토큰 유효성 확인
        log.info("[validateToken] 토큰 유효 체크 시작");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        log.info("[validateToken] 토큰 유효 체크 완료");
        return true;
    }

    public boolean validateRefreshToken(String token) {                         // 토큰 유효성 확인
        log.info("[validateRefreshToken] 토큰 유효 체크 시작");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        if (!claims.getBody().isEmpty()) {
            log.info("[validateRefreshToken] 토큰 유효 체크 완료");
            return true;
        }
        return false;
    }
}