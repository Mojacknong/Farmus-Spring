package com.modernfarmer.farmusspring.domain.auth.util.social.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class GoogleUserResponseDto implements SocialUserResponseDto {
    @JsonCreator
    public GoogleUserResponseDto(@JsonProperty("id") String id) {
        this.id = id;
    }

    @Override
    public String getId() {

        return this.id;
    }

    private String id;
    private String email;
    private String picture;
    private String name;
}