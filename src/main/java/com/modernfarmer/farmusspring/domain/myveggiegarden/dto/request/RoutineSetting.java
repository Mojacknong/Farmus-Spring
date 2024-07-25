package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoutineSetting {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String content;

    private boolean notify;

    private int period;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Long myVeggieId;

}
