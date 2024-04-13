package com.modernfarmer.farmusspring.domain.myveggiegarden.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter

public class SelectMyVeggieListResponse {

    List<SelectMyVeggieListDto> simpleMyVeggieList;

}
