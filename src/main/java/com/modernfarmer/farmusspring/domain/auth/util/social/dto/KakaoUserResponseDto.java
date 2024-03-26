package com.modernfarmer.farmusspring.domain.auth.util.social.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@AllArgsConstructor
public class KakaoUserResponseDto implements SocialUserResponseDto{

    private String id;
    @JsonCreator
    public KakaoUserResponseDto(@JsonProperty("id") String id) {
        this.id = id;
    }
    private UserProfileDTO  kakao_account;

    @Override
    public String getId() {
        return this.id;
    }
    @Setter
    @Getter
    public class UserProfileDTO {
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private ProfileData profile;
        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;




        @Getter
        @Setter
        public class ProfileData {
            private String nickname;
            private String thumbnail_image_Url;
            private String profile_image_url;
            private boolean isDefaultImage;
        }
    }



}
