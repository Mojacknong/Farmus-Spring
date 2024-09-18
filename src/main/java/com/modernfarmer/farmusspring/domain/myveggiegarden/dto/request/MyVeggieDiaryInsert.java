package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyVeggieDiaryInsert {
     @NotNull(message = "null 값을 가지면 안됩니다.")
     String content;
     Boolean isOpen;
     @NotNull(message = "null 값을 가지면 안됩니다.")
     String state;
     @NotNull(message = "null 값을 가지면 안됩니다.")
     Long myVeggieId;

}
