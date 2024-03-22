package com.modernfarmer.farmusspring.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class TokenResponseDto {

    private String accessToken;

    private String refreshToken;

    private boolean early;
}