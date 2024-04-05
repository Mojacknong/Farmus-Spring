package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettingMyVeggiDiaryRequest {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String conetent;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private boolean isOpen;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String iamge;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String state;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Long myVeggieId;

}
