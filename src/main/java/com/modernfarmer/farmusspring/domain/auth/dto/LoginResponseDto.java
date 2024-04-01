package com.modernfarmer.farmusspring.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class LoginResponseDto{

    protected String accessToken;

    protected String refreshToken;

    private boolean early;
}