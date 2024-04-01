package com.modernfarmer.farmusspring.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SetMotivationRequest {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private ArrayList<String> motivation;
}

