package com.modernfarmer.farmusspring.domain.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class AlarmStatus {

    private Boolean status;
}
