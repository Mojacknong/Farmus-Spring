package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class SelectDiaryOneResponse {

    private String image;
    private String content;
    private String date;
}
