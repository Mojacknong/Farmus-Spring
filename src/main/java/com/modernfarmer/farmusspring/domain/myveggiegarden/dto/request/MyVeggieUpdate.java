package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyVeggieUpdate {

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Long myVeggieId;


    @NotNull(message = "null 값을 가지면 안됩니다.")
    private String nickname;

    @NotNull(message = "null 값을 가지면 안됩니다.")
    private Date birth;

}
