package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettingMyVeggieRequest {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String nickname;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Date birh;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Long veggiInfoId;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String veggieName;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String veggieImage;
}
