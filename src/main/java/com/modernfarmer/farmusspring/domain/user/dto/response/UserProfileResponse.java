package com.modernfarmer.farmusspring.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class UserProfileResponse {


    private String nickName;
    private String userImageUrl;
    private long dDay;
}
