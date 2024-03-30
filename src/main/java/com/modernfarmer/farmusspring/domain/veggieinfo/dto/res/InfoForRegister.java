package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class InfoForRegister {

    private String name;
    private String veggieImage;
    private String period;
    private Difficulty difficulty;
}
