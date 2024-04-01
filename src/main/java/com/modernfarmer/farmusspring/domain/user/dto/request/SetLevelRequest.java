package com.modernfarmer.farmusspring.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SetLevelRequest {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private int time;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String skill;
}
